package edu.utsa.cs3443.clocker.controller;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.utsa.cs3443.clocker.R;
import edu.utsa.cs3443.clocker.model.ReportModel;

public class ReportController {

    private ReportModel reportModel;

    public ReportController() {
        this.reportModel = new ReportModel();
    }
    /**
     * Converts a comma-separated string of times into a list of long values.
     *
     * @param timesString The comma-separated string of times.
     * @return The list of long values representing times.
     */
    public List<Long> convertToLongList(String timesString) {
        List<Long> timesList = new ArrayList<>();
        String[] timesArray = timesString.split(",");
        for (String time : timesArray) {
            if (!time.isEmpty()) {
                timesList.add(Long.parseLong(time));
            }
        }
        return timesList;
    }
    /**
     * Displays clock times in a report table and scrolls to the bottom of the ScrollView.
     *
     * @param context          The context of the activity.
     * @param clockTimesList   The list of long values representing clock times.
     * @param reportTable      The TableLayout for displaying the report.
     * @param reportScrollView The ScrollView containing the report table.
     */
    public void displayClockTimes(Context context, List<Long> clockTimesList, TableLayout reportTable, ScrollView reportScrollView) {
        // Iterate through the clock times list and add rows to the table
        for (int i = 0; i < clockTimesList.size(); i += 2) {
            long clockInTimeMillis = clockTimesList.get(i);
            long clockOutTimeMillis = (i + 1 < clockTimesList.size()) ? clockTimesList.get(i + 1) : 0;

            String formattedClockInTime = formatTimestamp(clockInTimeMillis);
            String formattedClockOutTime = (clockOutTimeMillis > 0) ? formatTimestamp(clockOutTimeMillis) : "N/A";

            TableRow row = new TableRow(context);

            // Add TextViews to the TableRow
            addTextViewToRow(row, context, formattedClockInTime);
            addTextViewToRow(row, context, formattedClockOutTime);
            addTextViewToRow(row, context, reportModel.calculateTotalTime(clockInTimeMillis, clockOutTimeMillis));

            // Add the TableRow to the TableLayout
            reportTable.addView(row);
        }

        // Scroll to the bottom of the ScrollView
        reportScrollView.post(new Runnable() {
            @Override
            public void run() {
                reportScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
    /**
     * Adds a TextView with specified text to a TableRow, applying padding and a background border.
     *
     * @param row     The TableRow to which the TextView is added.
     * @param context The context of the activity.
     * @param text    The text to be displayed in the TextView.
     */
    public void addTextViewToRow(TableRow row, Context context, String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        // Set background to create a border
        textView.setBackgroundResource(R.drawable.cell_border);
        row.addView(textView);
    }
    /**
     * Formats a timestamp in milliseconds into a string with the specified date format.
     *
     * @param timestampMillis The timestamp in milliseconds.
     * @return The formatted timestamp string.
     */
    private String formatTimestamp(long timestampMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(timestampMillis);
        return dateFormat.format(date);
    }
}
