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

    public String getFormattedClockInTime() {
        return formatTime(getLatestClockInTime());
    }

    public String getFormattedClockOutTime() {
        return formatTime(getLatestClockOutTime());
    }

    private String formatTime(long timeMillis) {
        if (timeMillis > 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return dateFormat.format(new Date(timeMillis));
        } else {
            return "N/A";
        }
    }

    public void clockIn() {
        long clockInTimeMillis = System.currentTimeMillis();
        clockTimesList.add(clockInTimeMillis);
    }

    public void clockOut() {
        long clockOutTimeMillis = System.currentTimeMillis();
        clockTimesList.add(clockOutTimeMillis);
    }

    public List<Long> getClockTimes() {
        return clockTimesList;
    }

    public String serializeClockTimes() {
        return TextUtils.join(",", clockTimesList);
    }

    public void saveClockTimes(String employeeUsername, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String serializedTimes = serializeClockTimes();
        editor.putString(employeeUsername + "_clockTimes", serializedTimes);
        editor.apply();
    }

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