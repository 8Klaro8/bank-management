package src.main.model.account;

import src.main.model.Bank;
import src.main.model.account.impl.Account;
import src.main.model.account.impl.Taxable;

public class Chequing extends Account implements Taxable {

    private static final double OVERDRAFT_FEE = 5.50;
    private static final double OVERDRAFT_LIMIT = -200;
    private static final double TAX = 0.15;
    private static final double TAXABLE_INCOME = 3000;
    public double overdraft;

    // Constr
    public Chequing(String id, String name, double balance) {
        super(id, name, balance);
    }

    // copy Constr
    public Chequing(Chequing source) {
        super(source);
    }

    // clone override method
    @Override
    public Account clone() {
        return new Chequing(this);
    }

    // Get
    public double getOverdraft() {
        return this.overdraft;
    }

    @Override
    public double tax(double income) {
        return income * TAX;
        // double tax = Math.max(0, income - TAXABLE_INCOME) * TAX;
        
    }

    // Deposit
    @Override
    public void deposit(double depositAmount) {
        if (depositAmount > TAXABLE_INCOME) {
            super.setBalance(super.round(super.getBalance() + depositAmount - tax(depositAmount))); // if deposit amount greater than 3000 then tax it with 15%
            return;
        }
        super.setBalance(super.round(super.getBalance() + depositAmount));
    }

    // Withdraw
    @Override
    public boolean withdraw(double withdrawAmount) {
        // overdraft fee
        if ((super.getBalance() - withdrawAmount < OVERDRAFT_LIMIT)) {
            System.out.println("Overdraft limit exceeds $200");
            return false;
        } else if (withdrawAmount > super.getBalance()) {
            super.setBalance(super.round(super.getBalance() - withdrawAmount - OVERDRAFT_FEE));
        }

        else {
            super.setBalance(super.round(super.getBalance() - withdrawAmount));
        }
        return true;
    }
}
