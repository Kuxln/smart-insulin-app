package com.kuxln.smartinsulinbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "meal_records")
@Getter
@Setter
@NoArgsConstructor
public class MealRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String mealName;

    /** Кількість вуглеводів у їжі (грами) */
    @Column(nullable = false)
    private Double carbohydratesG;

    /** Глікемічний індекс страви (1–100) */
    private Integer glycemicIndex;

    @Column(nullable = false)
    private Instant mealTime;

    private String notes;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
