package edu.utsa.cs3443.clocker.controller;


import android.content.Context;
import android.widget.TableLayout;

import edu.utsa.cs3443.clocker.AdminActivity;
import edu.utsa.cs3443.clocker.model.AdminModel;

public class AdminController {
    private AdminModel model;

    public AdminController(Context context) {
        this.model = new AdminModel(context);
    }

    public void addData(String id, String username, String password, String role) {
        model.appendDataToCSV(id, username, password, role);
    }

    public void removeData(String idToRemove) {
        model.removeDataFromCSV(idToRemove);
    }

    public void editData(String idToEdit, String newUsername, String newPassword, String newRole) {
        model.editDataInCSV(idToEdit, newUsername, newPassword, newRole);
    }

    public void logFileContent() {
        model.logFileContent();
    }

    public void displayCSVContent(AdminActivity adminActivity) {
        model.displayCSVContent(adminActivity);
    }
    public void removeTableRow(String idToRemove, TableLayout tableLayout) {
        model.removeTableRow(idToRemove, tableLayout);
    }
}