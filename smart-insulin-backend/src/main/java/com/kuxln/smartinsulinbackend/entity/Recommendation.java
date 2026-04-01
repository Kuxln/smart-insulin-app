package com.kuxln.smartinsulinbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@NoArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Тип рекомендації: розрахунок дози, прогноз, або сповіщення */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecommendationType recommendationType;

    /** Рекомендована доза інсуліну (Од), якщо застосовно */
    private Double recommendedDose;

    /** Прогнозований рівень глюкози (ммоль/л) */
    private Double predictedGlucose;

    /** Горизонт прогнозу (хвилини) */
    private Integer predictionHorizonMinutes;

    /** JSON з контекстними даними, що використовувались під час розрахунку */
    @Column(columnDefinition = "TEXT")
    private String contextJson;

    /** Текст рекомендації для відображення користувачу */
    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Boolean isRead = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
