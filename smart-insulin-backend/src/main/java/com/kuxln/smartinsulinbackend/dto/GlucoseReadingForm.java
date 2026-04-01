package com.kuxln.smartinsulinbackend.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class GlucoseReadingForm {
    private Double glucoseValue = 0.0;
    private String measurementType = "MANUAL";

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime measuredAt = LocalDateTime.now();

    private String notes;

    public Double getGlucoseValue() { return glucoseValue; }
    public void setGlucoseValue(Double glucoseValue) { this.glucoseValue = glucoseValue; }

    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) { this.measurementType = measurementType; }

    public LocalDateTime getMeasuredAt() { return measuredAt; }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public void setMeasuredAt(LocalDateTime measuredAt) { this.measuredAt = measuredAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
