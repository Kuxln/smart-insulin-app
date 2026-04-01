package com.kuxln.smartinsulinbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuxln.smartinsulinbackend.dto.GlucoseReadingForm;
import com.kuxln.smartinsulinbackend.dto.GlucoseReadingResponse;
import com.kuxln.smartinsulinbackend.dto.HbA1cResponse;
import com.kuxln.smartinsulinbackend.entity.GlucoseReading;
import com.kuxln.smartinsulinbackend.entity.MeasurementType;
import com.kuxln.smartinsulinbackend.entity.User;
import com.kuxln.smartinsulinbackend.repository.GlucoseReadingRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Transactional
public class GlucoseReadingService {
    private final GlucoseReadingRepository glucoseReadingRepository;
    private final HbA1cCalculator hbA1cCalculator;

    public GlucoseReadingService(GlucoseReadingRepository glucoseReadingRepository,
                                 HbA1cCalculator hbA1cCalculator) {
        this.glucoseReadingRepository = glucoseReadingRepository;
        this.hbA1cCalculator = hbA1cCalculator;
    }

    public GlucoseReadingResponse addReading(User user, GlucoseReadingForm form) {
        String notes = (form.getNotes() != null && !form.getNotes().isBlank())
                ? form.getNotes() : null;
        GlucoseReading reading = glucoseReadingRepository.save(new GlucoseReading(
                user,
                form.getGlucoseValue(),
                MeasurementType.valueOf(form.getMeasurementType()),
                form.getMeasuredAt().toInstant(ZoneOffset.UTC),
                notes
        ));
        return toResponse(reading);
    }

    @Transactional(readOnly = true)
    public Page<GlucoseReadingResponse> getReadings(Long userId, int page, int size) {
        return glucoseReadingRepository
                .findByUserIdOrderByMeasuredAtDesc(userId, PageRequest.of(page, size))
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public List<GlucoseReadingResponse> getRecentReadings(Long userId, int limit) {
        return glucoseReadingRepository
                .findByUserIdOrderByMeasuredAtDesc(userId, PageRequest.of(0, limit))
                .getContent()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void deleteReading(Long userId, Long readingId) {
        GlucoseReading reading = glucoseReadingRepository.findById(readingId)
                .orElseThrow(() -> new IllegalArgumentException("Показник не знайдено"));
        if (!reading.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Доступ заборонено");
        }
        glucoseReadingRepository.delete(reading);
    }

    @Transactional(readOnly = true)
    public HbA1cResponse calculateHbA1c(Long userId, Instant from, Instant to) {
        List<GlucoseReading> readings = glucoseReadingRepository
                .findByUserIdAndMeasuredAtBetweenOrderByMeasuredAtAsc(userId, from, to);

        if (readings.isEmpty()) {
            return new HbA1cResponse(0.0, 0, 0.0);
        }

        double[] values = readings.stream()
                .mapToDouble(GlucoseReading::getGlucoseValue)
                .toArray();
        double hba1c = hbA1cCalculator.calculateHbA1c(values);
        double avg = readings.stream()
                .mapToDouble(GlucoseReading::getGlucoseValue)
                .average()
                .orElse(0.0);

        return new HbA1cResponse(hba1c, values.length, avg);
    }

    private GlucoseReadingResponse toResponse(GlucoseReading r) {
        return new GlucoseReadingResponse(
                r.getId(),
                r.getGlucoseValue(),
                r.getMeasurementType().name(),
                LocalDateTime.ofInstant(r.getMeasuredAt(), ZoneId.systemDefault()),
                r.getNotes(),
                LocalDateTime.ofInstant(r.getCreatedAt(), ZoneId.systemDefault())
        );
    }
}
