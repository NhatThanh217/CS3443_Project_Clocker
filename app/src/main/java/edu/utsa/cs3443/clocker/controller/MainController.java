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

    public MainController(MainModel model, MainActivity view) {
        this.model = model;
        this.view = view;
    }

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

    private String getRole(String username, String password, List<String[]> accounts) {
        for (String[] account : accounts) {
            if (account.length == 4 && account[1].equals(username) && account[2].equals(password)) {
                return account[3];
            }
        }
        return null;
    }

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