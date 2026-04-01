package com.kuxln.smartinsulinbackend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuxln.smartinsulinbackend.dto.RegisterForm;
import com.kuxln.smartinsulinbackend.entity.User;
import com.kuxln.smartinsulinbackend.repository.UserRepository;

@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterForm form) {
        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email вже зареєстровано");
        }
        userRepository.save(new User(
                form.getEmail(),
                passwordEncoder.encode(form.getPassword()),
                form.getFullName(),
                form.getDiabetesType()
        ));
    }
}
