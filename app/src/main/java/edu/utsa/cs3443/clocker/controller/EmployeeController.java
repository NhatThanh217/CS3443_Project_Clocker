package edu.utsa.cs3443.clocker.controller;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import edu.utsa.cs3443.clocker.EmployeeActivity;
import edu.utsa.cs3443.clocker.ReportActivity;
import edu.utsa.cs3443.clocker.model.EmployeeModel;

public class EmployeeController {

    private EmployeeModel model;
    private EmployeeActivity view;
    /**
     * Initializes the EmployeeController with the associated view (EmployeeActivity) and model (EmployeeModel).
     *
     * @param view The associated EmployeeActivity.
     */
    public EmployeeController(EmployeeActivity view) {
        this.view = view;
        this.model = new EmployeeModel();
    }
    /**
     * Gets the formatted clock-in time from the model.
     *
     * @return The formatted clock-in time.
     */
    public String getClockInTime() {
        return model.getFormattedClockInTime();
    }
    /**
     * Gets the formatted clock-out time from the model.
     *
     * @return The formatted clock-out time.
     */
    public String getClockOutTime() {
        return model.getFormattedClockOutTime();
    }
    /**
     * Handles the click event of the "View Report" button.
     * Opens the ReportActivity with employee information and serialized clock times as extras.
     */
    public void onReportButtonClick() {
        Intent intent = new Intent(view, ReportActivity.class);
        intent.putExtra("EMPLOYEE_NAME", view.getEmployeeInfo());
        intent.putExtra("CLOCK_TIMES", model.serializeClockTimes());
        view.startActivity(intent);
    }
    /**
     * Handles the click event of the "Clock In" button.
     * Checks if the user is already clocked in and displays appropriate messages.
     * Clocks in the user if they are not currently clocked in.
     */
    public void onClockInButtonClick() {
        if (model.getLatestClockInTime() > model.getLatestClockOutTime()) {
            // The user is already clocked in, show a message to clock out first
            Toast.makeText(view, "You need to clock out first", Toast.LENGTH_SHORT).show();
        } else {
            // User is not currently clocked in, proceed with clocking in
            model.clockIn();

            // Display the updated clock-in time in the view
            view.updateClockInTime();

            // Enable the clock-out button
            view.enableClockOutButton();

            Toast.makeText(view, "Clock-in successful", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Handles the click event of the "Clock Out" button.
     * Records the clock-out time, updates the view, and saves the clock times to SharedPreferences.
     */
    public void onClockOutButtonClick() {
        // Record the clock-out time
        model.clockOut();

        // Display the updated clock-out time in the view
        view.updateClockOutTime();

        // Disable the clock-out button until the next clock-in
        view.disableClockOutButton();

        // Save the clock times to SharedPreferences
        model.saveClockTimes(view.getEmployeeInfo(), view.getApplicationContext());

        Toast.makeText(view, "Clock-out successful", Toast.LENGTH_SHORT).show();
    }
}