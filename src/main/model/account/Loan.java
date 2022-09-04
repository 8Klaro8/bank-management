package src.main.model.account;

import src.main.model.Bank;
import src.main.model.account.impl.Account;
import src.main.model.account.impl.Taxable;

public class Loan extends Account {
    private static final double INTEREST_RATE = 0.02;
    private static final double MAXIMUM_DEBT = 10000;

    // Constr.
    public Loan(String id, String name, double balance) {
        super(id, name, balance);
    }

    // copy Constr.
    public Loan(Loan source) {
        super(source);
    }

    // clone override method
    @Override
    public Account clone() {
        return new Loan(this);
    }

    // Deposit
    @Override
    public void deposit(double depositAmount) {
        super.setBalance(super.round(super.getBalance() - depositAmount));
    }

    // Withdraw
    @Override
    public boolean withdraw(double withdrawAmount) {
        if (super.getBalance() + withdrawAmount > MAXIMUM_DEBT) {
            System.out.println("Debt exceeds the $10.000 limit.");
            return false;
        } else {
            super.setBalance(super.round(super.getBalance() + withdrawAmount + (withdrawAmount * INTEREST_RATE)));
            return true;
        }
    }
}
