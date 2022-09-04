package src.main.model.account.impl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Scanner;

import src.main.model.Bank;

public abstract class Account{
    private String id;
    private String name;
    private double balance;
    private double debt;

    // constr.
    public Account(String id, String name, double balance) {
        if (id.isEmpty() || id == null ||
                name.isEmpty() || name == null ||
                balance < 0) {
            throw new IllegalArgumentException("Can't be empty or null field.");
        }
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    // copy constr.
    public Account(Account source) {
        if (source.id.isEmpty() || source.id == null ||
                source.name.isEmpty() || source.name == null ||
                balance < 0) {
            throw new IllegalArgumentException("Can't be empty or null field.");
        }
        this.id = source.id;
        this.name = source.name;
        this.balance = source.balance;
        this.debt = source.debt;
    }

    // Get n Set
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        if (id.isEmpty() || id == null) {
            throw new IllegalArgumentException("INVALID ID");
        }
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name.isEmpty() || name == null) {
            throw new IllegalArgumentException("INVALID name.");
        }
        this.name = name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // withdraw and deposit
    public abstract boolean withdraw(double amount);
    public abstract void deposit(double amount);
    public abstract Account clone();

    // round decimals
    protected double round(double amount) {
        DecimalFormat formatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        return Double.parseDouble(formatter.format(amount));
    }

    // toString
    @Override
    public String toString() {
        return "\n\t" + this.getClass().getSimpleName() + "\n\t------------------" +
                "\n\tID: " + this.id +
                "\n\tNAME: " + this.name +
                "\n\tBALANCE: " + this.balance;
    }
}
