package edu.utsa.cs3443.clocker.model;


import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReportModel {
    /**
     * Calculates the total time between clock in and clock out timestamps.
     *
     * @param clockInTimeMillis  The timestamp in milliseconds for clock in.
     * @param clockOutTimeMillis The timestamp in milliseconds for clock out.
     * @return A formatted string representing the total time worked, or "N/A" if clock out has not occurred.
     */
    public String calculateTotalTime(long clockInTimeMillis, long clockOutTimeMillis) {
        // Implement the calculation logic
        // For example:
        if (clockOutTimeMillis > 0) {
            long totalTimeMillis = clockOutTimeMillis - clockInTimeMillis;
            long hours = TimeUnit.MILLISECONDS.toHours(totalTimeMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTimeMillis) % TimeUnit.HOURS.toMinutes(1);

            // Calculate the total shift time and convert to hours
            long totalShiftHours = TimeUnit.MILLISECONDS.toHours(totalTimeMillis);

            return String.format(Locale.getDefault(), "%02d:%02d = %2d hours", hours, minutes, totalShiftHours);
        } else {
            return "N/A";
        }
    }
}