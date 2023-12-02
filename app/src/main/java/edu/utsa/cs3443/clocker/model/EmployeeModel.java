package edu.utsa.cs3443.clocker.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmployeeModel {

    private static final String PREFS_NAME = "MyPrefs";

    private List<Long> clockTimesList = new ArrayList<>();
    /**
     * Gets the formatted clock-in time as a string.
     *
     * @return The formatted clock-in time or "N/A" if no clock-in time is recorded.
     */
    public String getFormattedClockInTime() {
        return formatTime(getLatestClockInTime());
    }
    /**
     * Gets the formatted clock-out time as a string.
     *
     * @return The formatted clock-out time or "N/A" if no clock-out time is recorded.
     */
    public String getFormattedClockOutTime() {
        return formatTime(getLatestClockOutTime());
    }
    /**
     * Formats a time in milliseconds into a string with the specified date format.
     *
     * @param timeMillis The time in milliseconds to be formatted.
     * @return The formatted time as a string.
     */
    private String formatTime(long timeMillis) {
        if (timeMillis > 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return dateFormat.format(new Date(timeMillis));
        } else {
            return "N/A";
        }
    }
    /**
     * Records a clock-in time by adding the current time in milliseconds to the clock times list.
     */
    public void clockIn() {
        long clockInTimeMillis = System.currentTimeMillis();
        clockTimesList.add(clockInTimeMillis);
    }
    /**
     * Records a clock-out time by adding the current time in milliseconds to the clock times list.
     */
    public void clockOut() {
        long clockOutTimeMillis = System.currentTimeMillis();
        clockTimesList.add(clockOutTimeMillis);
    }
    /**
     * Gets the list of recorded clock times.
     *
     * @return The list of clock times in milliseconds.
     */
    public List<Long> getClockTimes() {
        return clockTimesList;
    }
    /**
     * Serializes the list of clock times into a comma-separated string.
     *
     * @return The serialized string of clock times.
     */
    public String serializeClockTimes() {
        return TextUtils.join(",", clockTimesList);
    }
    /**
     * Saves the serialized clock times to SharedPreferences for a specific employee.
     *
     * @param employeeUsername The username of the employee.
     * @param context          The context of the application.
     */
    public void saveClockTimes(String employeeUsername, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String serializedTimes = serializeClockTimes();
        editor.putString(employeeUsername + "_clockTimes", serializedTimes);
        editor.apply();
    }
    /**
     * Gets the latest recorded clock-out time from the list.
     *
     * @return The latest clock-out time in milliseconds or 0 if no clock-out time is recorded.
     */
    public long getLatestClockInTime() {
        for (int i = clockTimesList.size() - 1; i >= 0; i--) {
            if (i % 2 == 0) {
                return clockTimesList.get(i);
            }
        }
        return 0;
    }

    public long getLatestClockOutTime() {
        for (int i = clockTimesList.size() - 1; i >= 0; i--) {
            if (i % 2 == 1) {
                return clockTimesList.get(i);
            }
        }
        return 0;
    }

}