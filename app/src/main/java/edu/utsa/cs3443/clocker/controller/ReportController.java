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

    public void addTextViewToRow(TableRow row, Context context, String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        // Set background to create a border
        textView.setBackgroundResource(R.drawable.cell_border);
        row.addView(textView);
    }

    private String formatTimestamp(long timestampMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(timestampMillis);
        return dateFormat.format(date);
    }
}
