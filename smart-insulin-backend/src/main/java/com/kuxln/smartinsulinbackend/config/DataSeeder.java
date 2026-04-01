package com.kuxln.smartinsulinbackend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.kuxln.smartinsulinbackend.entity.User;
import com.kuxln.smartinsulinbackend.entity.UserProfile;
import com.kuxln.smartinsulinbackend.repository.UserProfileRepository;
import com.kuxln.smartinsulinbackend.repository.UserRepository;

import java.util.List;

@Component
@Profile("!prod")
public class DataSeeder implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      UserProfileRepository userProfileRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        seedUsers();
    }

    private void seedUsers() {
        List<SeedUser> seeds = List.of(
            new SeedUser("patient1@example.com", "Test1234!", "Олена Коваленко", 1,
                new SeedProfile(62.0, 165.0, 2.5, 10.0, 4.0, 7.8, 4.0, "Lantus", "NovoRapid")),
            new SeedUser("patient2@example.com", "Test1234!", "Микола Бондаренко", 2,
                new SeedProfile(88.0, 178.0, 3.0, 12.0, 4.4, 8.3, 5.0, "Tresiba", "Humalog"))
        );

        for (SeedUser seed : seeds) {
            if (userRepository.findByEmail(seed.email()).isPresent()) {
                log.debug("Seeder: користувач {} вже існує, пропускаємо", seed.email());
                continue;
            }

            User user = userRepository.save(new User(
                    seed.email(),
                    passwordEncoder.encode(seed.rawPassword()),
                    seed.fullName(),
                    seed.diabetesType()
            ));

            UserProfile profile = new UserProfile(user);
            profile.setWeightKg(seed.profile().weightKg());
            profile.setHeightCm(seed.profile().heightCm());
            profile.setInsulinSensitivityFactor(seed.profile().insulinSensitivityFactor());
            profile.setInsulinToCarbRatio(seed.profile().insulinToCarbRatio());
            profile.setTargetGlucoseMin(seed.profile().targetGlucoseMin());
            profile.setTargetGlucoseMax(seed.profile().targetGlucoseMax());
            profile.setDurationOfInsulinAction(seed.profile().durationOfInsulinAction());
            profile.setBasalInsulinType(seed.profile().basalInsulinType());
            profile.setBolusInsulinType(seed.profile().bolusInsulinType());
            userProfileRepository.save(profile);

            log.info("Seeder: створено тестового користувача {} ({})", seed.fullName(), seed.email());
        }
    }

    private record SeedUser(String email, String rawPassword, String fullName,
                            Integer diabetesType, SeedProfile profile) {}

    private record SeedProfile(Double weightKg, Double heightCm,
                               Double insulinSensitivityFactor, Double insulinToCarbRatio,
                               Double targetGlucoseMin, Double targetGlucoseMax,
                               Double durationOfInsulinAction,
                               String basalInsulinType, String bolusInsulinType) {}
}
