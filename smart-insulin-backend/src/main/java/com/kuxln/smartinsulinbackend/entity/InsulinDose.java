package com.kuxln.smartinsulinbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "insulin_doses")
@Getter
@Setter
@NoArgsConstructor
public class InsulinDose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Кількість одиниць інсуліну */
    @Column(nullable = false)
    private Double doseUnits;

    /** Тип дози: BOLUS (їдальній), BASAL (базальний), CORRECTION (корекційний) */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DoseType doseType;

    private String insulinType;

    @Column(nullable = false)
    private Instant injectedAt;

    /** Прийом їжі, з яким пов'язане введення (необов'язково) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_record_id")
    private MealRecord mealRecord;

    /** Рівень глюкози до введення інсуліну (ммоль/л) */
    private Double glucoseBefore;

    private String notes;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
