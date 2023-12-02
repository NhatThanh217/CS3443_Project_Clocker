package edu.utsa.cs3443.clocker.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;


import edu.utsa.cs3443.clocker.R;
/**
 * Initializes the AdminModel with the given Android context.
 *
 */
public class AdminModel {
    private Context context;

    public AdminModel(Context context) {
        this.context = context;
    }
    /**
     * Appends user account data to the CSV file.
     *
     * @param id       The ID of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param role     The role of the user.
     */
    public void appendDataToCSV(String id, String username, String password, String role) {
        try {
            File file = new File(context.getFilesDir(), "accounts.csv");
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            String data = id + "," + username + "," + password + "," + role;
            printWriter.println(data);

            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();

            logFileContent();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("AppendData", "Error appending data to CSV: " + e.getMessage());
        }
    }
    /**
     * Removes user account data from the CSV file based on the specified ID.
     *
     * @param idToRemove The ID of the user to be removed.
     */
    public void removeDataFromCSV(String idToRemove) {
        try {
            File file = new File(context.getFilesDir(), "accounts.csv");
            if (!file.exists()) {
                Log.e("FileOperation", "File does not exist: " + file.getAbsolutePath());
                return;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.startsWith(idToRemove + ",")) {
                    stringBuilder.append(line).append("\n");
                } else {
                    Log.d("RemoveData", "Removed line: " + line);
                }
            }

            bufferedReader.close();
            fileReader.close();

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            printWriter.println(stringBuilder.toString());

            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();

            logFileContent();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FileOperation", "Error removing data from CSV: " + e.getMessage());
        }
    }

    /**
     * Edits user account data in the CSV file based on the specified ID.
     *
     * @param idToEdit      The ID of the user to be edited.
     * @param newUsername   The new username for the user.
     * @param newPassword   The new password for the user.
     * @param newRole       The new role for the user.
     */
    public void editDataInCSV(String idToEdit, String newUsername, String newPassword, String newRole) {
        try {
            File file = new File(context.getFilesDir(), "accounts.csv");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(idToEdit + ",")) {
                    line = idToEdit + "," + newUsername + "," + newPassword + "," + newRole;
                    Log.d("EditData", "Edited line: " + line);
                }
                stringBuilder.append(line).append("\n");
            }

            bufferedReader.close();
            fileReader.close();

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            printWriter.println(stringBuilder.toString());

            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();

            logFileContent();

            Log.d("EditData", "Data edited in CSV");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("EditData", "Error editing data in CSV: " + e.getMessage());
        }
    }
    /**
     * Logs the content of the CSV file.
     */
    public void logFileContent() {
        try {
            FileReader fileReader = new FileReader(new File(context.getFilesDir(), "accounts.csv"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            Log.d("FileContent", "File content:\n" + stringBuilder.toString());

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Displays the content of the CSV file in a TableLayout in the specified activity.
     *
     * @param activity The activity in which to display the CSV content.
     */
    public void displayCSVContent(Activity activity) {
        try {
            File internalFile = new File(context.getFilesDir(), "accounts.csv");

            if (!internalFile.exists()) {
                copyFileFromAssets(internalFile);
            }

            FileInputStream fis = context.openFileInput("accounts.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // Use the activity reference to find the TableLayout
            TableLayout tableLayout = activity.findViewById(R.id.accountTable);
            tableLayout.removeAllViews();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] values = line.split(",");
                TableRow tableRow = new TableRow(context);

                for (String value : values) {
                    TextView textView = new TextView(context);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1f);
                    textView.setLayoutParams(params);
                    textView.setText(value);
                    textView.setPadding(8, 8, 8, 8);
                    textView.setBackgroundResource(R.drawable.cell_border);
                    tableRow.addView(textView);
                }

                tableLayout.addView(tableRow);
            }

            bufferedReader.close();
            inputStreamReader.close();
            fis.close();

            Log.d("FileRead", "Read successful");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FileRead", "Error reading file: " + e.getMessage());
        }
    }/**
     * Removes a TableRow from the TableLayout based on the specified ID.
     *
     * @param idToRemove   The ID of the user whose TableRow should be removed.
     * @param tableLayout  The TableLayout containing the TableRows.
     */
    public void removeTableRow(String idToRemove, TableLayout tableLayout) {
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View view = tableLayout.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow row = (TableRow) view;

                // Get the TextView in the TableRow
                TextView idTextView = (TextView) row.getChildAt(0);

                // Check if the ID matches the one to be removed
                if (idTextView.getText().toString().equals(idToRemove)) {
                    // Remove the TableRow from the TableLayout
                    tableLayout.removeView(row);
                    Log.d("RemoveRow", "Removed TableRow with ID: " + idToRemove);
                    break;  // Exit the loop after removal
                }
            }
        }
    } /**
     * Copies the CSV file from the assets folder to the internal storage.
     *
     * @param destinationFile The destination file in the internal storage.
     * @throws IOException If an error occurs during file copy.
     */
    public void copyFileFromAssets(File destinationFile) throws IOException {
        InputStream inputStream = context.getAssets().open("accounts.csv");
        FileOutputStream outputStream = new FileOutputStream(destinationFile);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();
    }
}