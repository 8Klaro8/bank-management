package src.main.model;

import static org.junit.jupiter.api.DynamicTest.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import src.main.model.account.Chequing;
import src.main.model.account.Loan;
import src.main.model.account.Savings;
import src.main.model.account.impl.Account;
import src.main.model.account.impl.Taxable;

public class Bank {

    ArrayList<Account> accounts;
    ArrayList<Transaction> transactions;

    // constr.
    public Bank() {
        this.accounts = new ArrayList<Account>();
        this.transactions = new ArrayList<Transaction>();
    }

    public void addAccount(Account account) {
        this.accounts.add(account.clone());
    }

    private void addTransaction(Transaction transaction) {
        this.transactions.add(new Transaction(transaction));
    }

    private void withdrawTransaction(Transaction transaction) {
        if (getAccount(transaction.getId()).withdraw(transaction.getAmount())) {
            addTransaction(transaction);
        }
    }

    private void depositTransaction(Transaction transaction) {
        getAccount(transaction.getId()).deposit(transaction.getAmount());
        addTransaction(transaction);
    }

    public void executeTransaction(Transaction transaction) {
        switch (transaction.getTransaction()) {
            case WITHDRAW: withdrawTransaction(transaction); break;
            case DEPOSIT: depositTransaction(transaction); break;
            default: break;
        }
    }

    private double getIncome(Taxable account) {
        Transaction[] transactions = getTransactions(((Chequing)account).getId());
        return Arrays.stream(transactions)
        .mapToDouble((transaction) ->{
            switch (transaction.getTransaction()){
                case WITHDRAW: return -transaction.getAmount();
                case DEPOSIT: return transaction.getAmount();
                default: return 0;
            }
        }).sum();
    }

    public void deductTaxes() {
        for (Account account : accounts) {
            if (Taxable.class.isAssignableFrom(account.getClass())) {
                Taxable taxable = (Taxable)account;
                taxable.tax(getIncome((Taxable)account));
            }
        }
    }

    // Get Transaction by account Id
    public Transaction[] getTransactions(String accountId) {

        // METHOD I. - get transactions by for loop
        // ArrayList<Transaction> transiList = new ArrayList<Transaction>();
        // for (int i = 0; i < transactions.size(); i++) {
        // if (transactions.get(i).getId().equals(accountId)) {
        // transiList.add(transactions.get(i));
        // }
        // }

        // return transiList.toArray(new Transaction[transiList.size()]);

        // METHOD II. - get trasactions by stream pipeline
        List<Transaction> transaction = transactions.stream()
                .filter((trans) -> trans.getId().equals(accountId))
                .collect(Collectors.toList());

        return transaction.toArray(new Transaction[transaction.size()]);
    }

    public Account getAccount(String transactionId) {
        return accounts.stream().filter((acc) -> acc.getId().equals(transactionId)).findFirst().orElse(null);

        // for (int i = 0; i < accounts.size(); i++) {
        // if (accounts.get(i).getId().equals(transactionId)) {
        // return accounts.get(i);
        // }
        // }
        // return null;
    }

    // Returns all transactions
    public Transaction[] getAllTransaction() {
        ArrayList<Transaction> myTrnasArray = new ArrayList<Transaction>();
        for (Transaction trans : transactions) {
            myTrnasArray.add(trans);
        }
        Transaction[] transArray = myTrnasArray.toArray(new Transaction[myTrnasArray.size()]);

        return transArray;
    }

    // SOLUTION I.
    // ArrayList<Transaction> currentIdsTransactions = new ArrayList<Transaction>();
    // for (int i = 0; i < transactions.size(); i++) {
    // if (accountId.equals(transactions.get(i).getId())) {
    // currentIdsTransactions.add(transactions.get(i));
    // }
    // }
    // Object[] currTransObjectArray = currentIdsTransactions.toArray();
    // Transaction[] transactionTransArray = (Transaction[])currTransObjectArray;
    // return transactionTransArray;
}
