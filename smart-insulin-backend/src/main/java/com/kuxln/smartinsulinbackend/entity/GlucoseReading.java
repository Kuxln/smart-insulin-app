package com.kuxln.smartinsulinbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "glucose_readings")
@Getter
@Setter
@NoArgsConstructor
public class GlucoseReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Рівень глюкози в крові (ммоль/л) */
    @Column(nullable = false)
    private Double glucoseValue;

    /** Тип вимірювання: CGM (безперервний моніторинг) або MANUAL (ручне) */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType = MeasurementType.MANUAL;

    @Column(nullable = false)
    private Instant measuredAt;

    private String notes;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public GlucoseReading(User user, Double glucoseValue, MeasurementType measurementType,
                          Instant measuredAt, String notes) {
        this.user = user;
        this.glucoseValue = glucoseValue;
        this.measurementType = measurementType;
        this.measuredAt = measuredAt;
        this.notes = notes;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
