
package edu.utsa.cs3443.clocker;
/**
 * CS 3443_ Application Programing
 * Fall 2023
 * @author Ho, Nhat Thanh (pkt062)
 * Project Clocker
 */
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.utsa.cs3443.clocker.controller.ReportController;

public class ReportActivity extends AppCompatActivity {

    private TableLayout reportTable;
    private ScrollView reportScrollView;
    private ReportController reportController;
    /**
     * Initializes the ReportActivity, sets up UI components, and handles button click events.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reportTable = findViewById(R.id.reportTable);
        reportScrollView = findViewById(R.id.reportScrollView);
        reportController = new ReportController();

        // Retrieve data from Intent
        String employeeName = getIntent().getStringExtra("EMPLOYEE_NAME");
        String clockTimesString = getIntent().getStringExtra("CLOCK_TIMES");

        // Set employee name in a TextView
        // Assuming you have a TextView with id 'employeeNameTextView' in your layout
         TextView employeeNameTextView = findViewById(R.id.employeeNameTextView);
         employeeNameTextView.setText("Report: " + employeeName);

        // Convert the comma-separated string to a list of Long values
        List<Long> clockTimesList = reportController.convertToLongList(clockTimesString);

        // Display clock times in the report
        reportController.displayClockTimes(this, clockTimesList, reportTable, reportScrollView);

        // Set up the Payroll button click listener
        Button payrollButton = findViewById(R.id.payrollButton);
        payrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Payroll button click
                generatePayroll();
            }
        });
    }

    /**
     * Generates and displays a simple toast message indicating that the payroll has been generated.
     */
    private void generatePayroll() {
        //  display a simple toast message
        Toast.makeText(this, "Payroll generated!", Toast.LENGTH_SHORT).show();
    }
}