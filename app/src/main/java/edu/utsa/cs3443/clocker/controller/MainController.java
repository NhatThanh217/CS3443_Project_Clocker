package edu.utsa.cs3443.clocker.controller;
import android.content.Intent;

import java.util.List;

import edu.utsa.cs3443.clocker.AdminActivity;
import edu.utsa.cs3443.clocker.EmployeeActivity;
import edu.utsa.cs3443.clocker.MainActivity;
import edu.utsa.cs3443.clocker.model.MainModel;

public class MainController {
    private MainModel model;
    private MainActivity view;
    /**
     * Constructs a MainController object with the specified MainModel and MainActivity.
     *
     * @param model The MainModel associated with the controller.
     * @param view  The MainActivity associated with the controller.
     */
    public MainController(MainModel model, MainActivity view) {
        this.model = model;
        this.view = view;
    }
    /**
     * Handles the login button click event by retrieving user input, validating credentials,
     * and navigating to the appropriate view based on the user's role.
     */
    public void onLoginButtonClick() {
        String username = view.getUsername();
        String password = view.getPassword();

        List<String[]> accounts = model.getAccounts();

        String role = getRole(username, password, accounts);

        if (role != null) {
            view.clearCredentials();
            view.requestFocusOnUsername();

            navigateToViewBasedOnRole(role, username);
        } else {
            view.showIncorrectCredentialsMessage();
        }
    }
    /**
     * Retrieves the user's role based on the provided username and password.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param accounts A list of user accounts to check for matching credentials.
     * @return The role associated with the provided credentials, or null if no match is found.
     */
    private String getRole(String username, String password, List<String[]> accounts) {
        for (String[] account : accounts) {
            if (account.length == 4 && account[1].equals(username) && account[2].equals(password)) {
                return account[3];
            }
        }
        return null;
    }
    /**
     * Navigates to the appropriate activity based on the user's role.
     *
     * @param role     The role of the user.
     * @param username The username of the logged-in user.
     */
    private void navigateToViewBasedOnRole(String role, String username) {
        Intent intent;
        if (role.equals("admin")) {
            intent = new Intent(view, AdminActivity.class);
            intent.putExtra("USERNAME", username);
        } else {
            intent = new Intent(view, EmployeeActivity.class);
            intent.putExtra("USERNAME", username);
        }

        view.startActivity(intent);
    }
}