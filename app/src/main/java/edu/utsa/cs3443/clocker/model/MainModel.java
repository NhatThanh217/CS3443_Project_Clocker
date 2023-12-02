package edu.utsa.cs3443.clocker.model;

import androidx.annotation.NonNull;

import java.util.List;

public class MainModel {
    private List<String[]> accounts;
    /**
     * Constructs a MainModel object with the specified list of user accounts.
     *
     * @param accounts The list of user accounts to be associated with the model.
     */
    public MainModel(List<String[]> accounts) {
        this.accounts = accounts;
    }
    /**
     * Retrieves the list of user accounts stored in the model.
     *
     * @return The list of user accounts.
     */
    public List<String[]> getAccounts() {
        return accounts;
    }
    /**
     * Sets the list of user accounts for the model.
     *
     * @param accounts The list of user accounts to be set.
     */
    public void setAccounts(List<String[]> accounts) {
        this.accounts = accounts;
    }
    /**
     * Returns a string representation of the MainModel object.
     *
     * @return A string representation of the object.
     */
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
