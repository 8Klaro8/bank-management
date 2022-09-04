package src.main.model.account;

import src.main.model.Bank;
import src.main.model.account.impl.Account;
import src.main.model.account.impl.Taxable;

public class Savings extends Account {
    private static final double WITHDRAWAL_FEE = 5;

    // constr.
    public Savings(String id, String name, double balance) {
        super(id, name, balance);
    }

    // copy constr.
    public Savings(Savings source) {
        super(source);
    }

    // clone override method
    @Override
    public Account clone() {
        return new Savings(this);
    }

    // deposit
    @Override
    public void deposit(double depositAmount) {
        super.setBalance(super.round(super.getBalance() + depositAmount));
    }

    // withdraw
    @Override
    public boolean withdraw(double withdrawAmount) {
        super.setBalance(super.round(super.getBalance() - WITHDRAWAL_FEE - withdrawAmount));
        return true;
    }

}
