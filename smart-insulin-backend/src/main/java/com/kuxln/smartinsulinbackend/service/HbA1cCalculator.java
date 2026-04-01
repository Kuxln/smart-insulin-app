package com.kuxln.smartinsulinbackend.service;

import org.springframework.stereotype.Component;

/**
 * Pure Java implementation of the HbA1c and Insulin on Board calculations,
 * replacing the native C library (hba1c-lib).
 *
 * HbA1c formula: (eAG + 2.594) / 1.594  (ADAG formula)
 * IOB formula:   Dose * (1 - t/DIA)      (linear decay model)
 */
@Component
public class HbA1cCalculator {
    private static final double ADAG_OFFSET = 2.594;
    private static final double ADAG_DIVISOR = 1.594;

    /**
     * Calculates estimated HbA1c (%) from an array of glucose readings (mmol/L).
     * Returns -1.0 if the array is empty or contains negative values.
     */
    public double calculateHbA1c(double[] measurements) {
        if (measurements == null || measurements.length == 0) return -1.0;
        double sum = 0.0;
        for (double v : measurements) {
            if (v < 0) return -1.0;
            sum += v;
        }
        double eAG = sum / measurements.length;
        return (eAG + ADAG_OFFSET) / ADAG_DIVISOR;
    }

    /**
     * Calculates Insulin on Board (Units) using a linear decay model.
     *
     * @param dose           Initial insulin dose (Units)
     * @param timeSinceInj   Time elapsed since injection (minutes)
     * @param dia            Duration of insulin action (minutes)
     */
    public double calculateIob(double dose, double timeSinceInj, double dia) {
        if (timeSinceInj <= 0) return dose;
        if (timeSinceInj >= dia) return 0.0;
        double iob = dose * (1.0 - timeSinceInj / dia);
        return Math.max(iob, 0.0);
    }
}
