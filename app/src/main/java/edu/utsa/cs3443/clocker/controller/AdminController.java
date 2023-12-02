package edu.utsa.cs3443.clocker.controller;


import android.content.Context;
import android.widget.TableLayout;

import edu.utsa.cs3443.clocker.AdminActivity;
import edu.utsa.cs3443.clocker.model.AdminModel;

public class AdminController {
    private AdminModel model;
    /**
     * Constructs an AdminController instance with the provided context to initialize the associated AdminModel.
     *
     * @param context The context used to initialize the AdminModel.
     */
    public AdminController(Context context) {
        this.model = new AdminModel(context);
    }
    /**
     * Adds data to the CSV file using the AdminModel's method for appending data.
     *
     * @param id       The ID of the user to add.
     * @param username The username of the user to add.
     * @param password The password of the user to add.
     * @param role     The role of the user to add.
     */
    public void addData(String id, String username, String password, String role) {
        model.appendDataToCSV(id, username, password, role);
    }
    /**
     * Removes data from the CSV file using the AdminModel's method for removing data.
     *
     * @param idToRemove The ID of the user whose data should be removed.
     */
    public void removeData(String idToRemove) {
        model.removeDataFromCSV(idToRemove);
    }
    /**
     * Edits data in the CSV file using the AdminModel's method for editing data.
     *
     * @param idToEdit     The ID of the user whose data should be edited.
     * @param newUsername  The new username to replace the existing username.
     * @param newPassword  The new password to replace the existing password.
     * @param newRole      The new role to replace the existing role.
     */
    public void editData(String idToEdit, String newUsername, String newPassword, String newRole) {
        model.editDataInCSV(idToEdit, newUsername, newPassword, newRole);
    }
    /**
     * Displays the content of the CSV file in the AdminActivity's UI using the AdminModel's method.
     *
     * @param adminActivity The AdminActivity instance where the CSV content should be displayed.
     */
    public void displayCSVContent(AdminActivity adminActivity) {
        model.displayCSVContent(adminActivity);
    }
}