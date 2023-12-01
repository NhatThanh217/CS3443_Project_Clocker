package edu.utsa.cs3443.clocker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.utsa.cs3443.clocker.controller.MainController;
import edu.utsa.cs3443.clocker.model.MainModel;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private MainController controller;

    public void setUsernameEditText(EditText usernameEditText) {
        this.usernameEditText = usernameEditText;
    }

    public void setPasswordEditText(EditText passwordEditText) {
        this.passwordEditText = passwordEditText;
    }

    private static final String CSV_FILE_NAME = "accounts.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);

        MainModel model = new MainModel(loadUserAccounts());
        controller = new MainController(model, this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onLoginButtonClick();
            }
        });
        // Add the following code to set the current date and time
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView timeTextView = findViewById(R.id.timeTextView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM-dd-yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        String currentDate = dateFormat.format(new Date());
        String currentTime = timeFormat.format(new Date());

        dateTextView.setText("Date: " + currentDate);
        timeTextView.setText("Time: " + currentTime);

        // Set the greeting based on the time of day
        TextView greetingTextView = findViewById(R.id.greetingTextView);
        setGreeting(greetingTextView);
        //Set help for forgot username or password by pop dialog
        TextView forgotTextView = findViewById(R.id.forgotTextView);
        forgotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordDialog();
            }
        });
    }
    public String getUsername() {
        return usernameEditText.getText().toString().trim();
    }

    public String getPassword() {
        return passwordEditText.getText().toString().trim();
    }

    public void showIncorrectCredentialsMessage() {
        Toast.makeText(this, "Incorrect username or password. Try again.", Toast.LENGTH_SHORT).show();
    }

    public void navigateToAdminView(String username) {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

    public void navigateToEmployeeView(String username) {
        Intent intent = new Intent(this, EmployeeActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }


    public void requestFocusOnUsername() {
        usernameEditText.requestFocus();
    }
    // Method to show the "Forgot Password" dialog
    private void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help!!!");
        builder.setMessage("Please contact pkt062@gmail.com for help. Thank you.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
            }
        });
        builder.show();
    }
    private void setGreeting(TextView greetingTextView) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH", Locale.getDefault());
        int currentHour = Integer.parseInt(timeFormat.format(new Date()));
        String greeting;
        String quote;
        if (currentHour >= 0 && currentHour < 12) {
            greeting = "Good morning!\n";
            quote = " \"Morning is wonderful. Its only drawback is that it comes at such an inconvenient time of day.\" - Glen Cook";
        } else if (currentHour >= 12 && currentHour < 18) {
            greeting = "Good afternoon!\n";
            quote = " \"The afternoon knows what the morning never suspected.\" - Robert Frost";
        } else {
            greeting = "Good evening!\n";
            quote = " \"Evenings allow you to forget the bitter worries of the day and get ready for the sweet dreams of night.\" - Unknown";
        }
        greetingTextView.setText(greeting + quote);
    }
    private void onLoginButtonClick() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        List<String[]> accounts = loadUserAccounts();

        String role = getRole(username, password, accounts);

        if (role != null) {
            // Clear credentials
            clearCredentials();
            // Set focus to the username field
            usernameEditText.requestFocus();

            navigateToViewBasedOnRole(role,username);
        } else {
            Toast.makeText(MainActivity.this, "Incorrect username or password. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    // Clear username and password fields
    public void clearCredentials() {
        usernameEditText.getText().clear();
        passwordEditText.getText().clear();
    }

    private List<String[]> loadUserAccounts() {
        List<String[]> accounts = new ArrayList<>();

        try {
            InputStream inputStream = getAssets().open(CSV_FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                accounts.add(parts);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    //accounts.csv(ID,username,password,role)
    private String getRole(String username, String password, List<String[]> accounts) {
        for (String[] account : accounts) {
            if (account.length == 4 && account[1].equals(username) && account[2].equals(password)) {
                return account[3]; // Return the role
            }
        }
        return null; // Return null if no match is found
    }

    private void navigateToViewBasedOnRole(String role, String username) {
        Intent intent;
        if (role.equals("admin")) {
            intent = new Intent(MainActivity.this, AdminActivity.class);
            intent.putExtra("USERNAME", username); // Pass the username as an extra

            // case "employee":
        } else {
            intent = new Intent(MainActivity.this, EmployeeActivity.class);
            intent.putExtra("USERNAME", username); // Pass the username as an extra
        }

        startActivity(intent);
    }

}