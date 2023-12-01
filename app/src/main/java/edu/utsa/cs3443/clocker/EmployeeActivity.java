package edu.utsa.cs3443.clocker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.utsa.cs3443.clocker.controller.EmployeeController;

public class EmployeeActivity extends AppCompatActivity {

    private TextView employeeInfoTextView;
    private TextView dateTimeTextView;
    private Button clockInButton;
    private Button clockOutButton;
    private TextView clockInTimeTextView;
    private TextView clockOutTimeTextView;
    private Button reportButton;

    private EmployeeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        employeeInfoTextView = findViewById(R.id.employeeInfoTextView);
        dateTimeTextView = findViewById(R.id.dateTimeTextView);
        clockInButton = findViewById(R.id.clockInButton);
        clockOutButton = findViewById(R.id.clockOutButton);
        clockInTimeTextView = findViewById(R.id.clockInTimeTextView);
        clockOutTimeTextView = findViewById(R.id.clockOutTimeTextView);
        reportButton = findViewById(R.id.reportButton);

        controller = new EmployeeController(this);

        String employeeInfo = getIntent().getStringExtra("USERNAME");
        employeeInfoTextView.setText(employeeInfo);

        updateDateTime();

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onReportButtonClick();
            }
        });

        clockInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onClockInButtonClick();
            }
        });

        clockOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onClockOutButtonClick();
            }
        });

        clockOutButton.setEnabled(false);
    }

    public void updateClockInTime() {
        clockInTimeTextView.setText("Clock-in Time: " + controller.getClockInTime());
    }

    public void updateClockOutTime() {
        clockOutTimeTextView.setText("Clock-out Time: " + controller.getClockOutTime());
    }

    public void enableClockOutButton() {
        clockOutButton.setEnabled(true);
    }

    public void disableClockOutButton() {
        clockOutButton.setEnabled(false);
    }

    public String getEmployeeInfo() {
        return employeeInfoTextView.getText().toString();
    }

    private void updateDateTime() {
        // Get the current date and time directly in the EmployeeActivity
        String currentDateTime = getCurrentDateTime(); // Replace this with the actual method call

        // Update the date/time TextView
        dateTimeTextView.setText("Current Date/Time: " + currentDateTime);
    }
    // Replace this with the actual method to get the formatted date/time
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}