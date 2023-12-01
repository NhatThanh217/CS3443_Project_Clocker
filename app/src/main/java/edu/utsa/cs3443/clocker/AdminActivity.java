package edu.utsa.cs3443.clocker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.utsa.cs3443.clocker.controller.AdminController;

public class AdminActivity extends AppCompatActivity {
    private AdminController controller;
    private TextView dateTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        controller = new AdminController(this);

        dateTimeTextView = findViewById(R.id.dateTimeTextView);

        // Retrieve the username from the intent extras
        String userName = getIntent().getStringExtra("USERNAME");

        // Display the username at the top of the screen
        TextView adminUsernameTextView = findViewById(R.id.adminUsernameTextView);
        adminUsernameTextView.setText(userName);

        // Display current date/time
        updateDateTime();

        // Add button
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();

            }
        });

        // Remove button
        Button removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemoveDialog();

            }
        });

        // Edit button
        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        // Display data of file accounts.csv when the activity is created
        controller.displayCSVContent(this);
    }

    private void showAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add, null);

         EditText idEditText = dialogView.findViewById(R.id.idEditText);
         EditText usernameEditText = dialogView.findViewById(R.id.usernameEditText);
         EditText passwordEditText = dialogView.findViewById(R.id.passwordEditText);
         EditText roleEditText = dialogView.findViewById(R.id.roleEditText);

        dialogBuilder.setView(dialogView)
                .setTitle("Add Data")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = idEditText.getText().toString();
                        String username = usernameEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        String role = roleEditText.getText().toString();

                        controller.addData(id, username, password, role);

                        // Optionally, you can notify the user that data was added
                        Toast.makeText(AdminActivity.this, "Data added to CSV", Toast.LENGTH_SHORT).show();
                        // Update UI after adding data
                        controller.displayCSVContent(AdminActivity.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Cancel, do nothing
                    }
                });

        dialogBuilder.create().show();
    }

    private void showRemoveDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_remove, null);

        final EditText idToRemoveEditText = dialogView.findViewById(R.id.idToRemoveEditText);

        dialogBuilder.setView(dialogView)
                .setTitle("Remove Data")
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idToRemove = idToRemoveEditText.getText().toString();
                        controller.removeData(idToRemove);

                        Toast.makeText(AdminActivity.this, "Data removed from CSV", Toast.LENGTH_SHORT).show();
                        // Update UI after adding data
                        controller.displayCSVContent(AdminActivity.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Cancel, do nothing
                    }
                });

        dialogBuilder.create().show();
    }

    private void showEditDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit, null);

        EditText idEditText = dialogView.findViewById(R.id.idEditText);
        EditText usernameEditText = dialogView.findViewById(R.id.usernameEditText);
        EditText passwordEditText = dialogView.findViewById(R.id.passwordEditText);
        EditText roleEditText = dialogView.findViewById(R.id.roleEditText);

        dialogBuilder.setView(dialogView)
                .setTitle("Edit Data")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idToEdit = idEditText.getText().toString();
                        String newUsername = usernameEditText.getText().toString();
                        String newPassword = passwordEditText.getText().toString();
                        String newRole = roleEditText.getText().toString();

                        controller.editData(idToEdit, newUsername, newPassword, newRole);

                        Toast.makeText(AdminActivity.this, "Data edited in CSV", Toast.LENGTH_SHORT).show();
                        // Update UI after adding data
                        controller.displayCSVContent(AdminActivity.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Cancel, do nothing
                    }
                });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        // Clear the hint text to allow the user to input the ID
        idEditText.setHint("Enter ID to Edit");
    }

    private void updateDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateTime = dateFormat.format(new Date());

        dateTimeTextView.setText("Current Date/Time: " + currentDateTime);
    }
}