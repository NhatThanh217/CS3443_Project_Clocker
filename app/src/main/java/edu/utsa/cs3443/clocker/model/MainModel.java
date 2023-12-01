package edu.utsa.cs3443.clocker.model;

import androidx.annotation.NonNull;

import java.util.List;

public class MainModel {
    private List<String[]> accounts;

    public MainModel(List<String[]> accounts) {
        this.accounts = accounts;
    }

    public List<String[]> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String[]> accounts) {
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
