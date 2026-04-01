package com.kuxln.smartinsulinbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /** Вага пацієнта (кг) */
    private Double weightKg;

    /** Зріст пацієнта (см) */
    private Double heightCm;

    /** Коефіцієнт чутливості до інсуліну: на скільки ммоль/л знижується глюкоза після 1 Од */
    private Double insulinSensitivityFactor;

    /** Інсуліно-вуглеводне співвідношення: скільки г вуглеводів перекриває 1 Од */
    private Double insulinToCarbRatio;

    /** Нижня межа цільового рівня глюкози (ммоль/л) */
    private Double targetGlucoseMin;

    /** Верхня межа цільового рівня глюкози (ммоль/л) */
    private Double targetGlucoseMax;

    /** Тривалість дії інсуліну (год), зазвичай 3–8 */
    private Double durationOfInsulinAction;

    /** Тип базального інсуліну (напр. Lantus, Tresiba) */
    private String basalInsulinType;

    /** Тип болюсного інсуліну (напр. NovoRapid, Humalog) */
    private String bolusInsulinType;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    public UserProfile(User user) {
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
