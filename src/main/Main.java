package src.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.prefs.BackingStoreException;

import javax.xml.crypto.Data;

import src.main.model.Bank;
import src.main.model.Transaction;
import src.main.model.account.Chequing;
import src.main.model.account.Loan;
import src.main.model.account.Savings;
import src.main.model.account.impl.Account;

public class Main {

    static String ACCOUNTS_FILE = "src/main/data/accounts.txt";
    static String TRANSACTIONS_FILE = "src/main/data/transactions.txt";

    public static void main(String[] args) throws FileNotFoundException{

        // Transaction trans = new Transaction(1546646400, Transaction.TransactionType.DEPOSIT, "6feaa39d-342b-4195-991d-92e18465d457",526.29);
        // System.out.println(trans.returnDate());
        // System.exit(0);

        Bank bank = new Bank();

        ArrayList<Account> allAccount =  returnAccounts();
        loadAccounts(bank, allAccount);
        ArrayList<Transaction> allTransaction =  returnTransactions();
        for (Transaction transaction : allTransaction) {
            System.out.println(transaction.getTimestamp());
        }

        runTransaction(allTransaction, bank);

        for (Account account : allAccount) {
            System.out.println("\n\t\t\t\t\t\t\t ACCOUNT\n\n\n\t" + account + "\n\n");
            transactionHistroy(account.getId(), bank);
        }
    }

    public static Account createObject(String[] values) {
        switch (values[0]) {
            case "Chequing":
                return new Chequing(values[1], values[2], Double.parseDouble(values[3]));
            case "Loan":
                return new Loan(values[1], values[2], Double.parseDouble(values[3]));
            case "Savings":
                return new Chequing(values[1], values[2], Double.parseDouble(values[3]));
            default:
                return null;
        }
    }

    // Returns all account from "accounts.txt"
    public static ArrayList<Account> returnAccounts() throws FileNotFoundException{
            ArrayList<Account> createdAccounts = new ArrayList<Account>();
            File accountsTextFile = new File("src\\main\\data\\accounts.txt");
            Scanner scanner = new Scanner(accountsTextFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splittedLine = line.split(",");

                createdAccounts.add(createObject(splittedLine));
            }

            return createdAccounts;
    }

    // Populates all the account to bank
    public static void loadAccounts(Bank bank, ArrayList<Account> account) {
        for (int i = 0; i < account.size(); i++) {
            bank.addAccount(account.get(i));
        }
    }


    public static void wait(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    // Gets all the transactions from "transaction.txt"
    public static ArrayList<Transaction> returnTransactions() throws FileNotFoundException {
        File transactionFile = new File("src\\main\\data\\transactions.txt");
        Scanner transactionScenenr = new Scanner(transactionFile);

        ArrayList<Transaction> transactionsArrayList = new ArrayList<Transaction>();
        while (transactionScenenr.hasNextLine()) {

            String line = transactionScenenr.nextLine();
            String[] splittedLine = line.split(",");
            Transaction newTransaction = new Transaction(Long.parseLong(splittedLine[0]), Transaction.TransactionType.valueOf(splittedLine[1]), splittedLine[2], Double.parseDouble(splittedLine[3]));
            transactionsArrayList.add(newTransaction);
        }
        // Collections.sort(transactionsArrayList); // redefines timestemp - BUG
        transactionScenenr.close();
        return transactionsArrayList;
    }

    public static void runTransaction(ArrayList<Transaction> transactions, Bank bank) {
        for (Transaction transaction : transactions) {
            bank.executeTransaction(transaction);
        }
    }

    public static void transactionHistroy(String id, Bank bank) {
        Transaction[] correspondingTransactions = bank.getTransactions(id);

        System.out.println("\t\t\t\t   TRANSACTION HISTORY\n\t");
        for (Transaction transaction : correspondingTransactions) {
            wait(300);
            System.out.println("\t" + transaction + "\n");
        }
        System.out.println("\n\t\t\t\t\tAFTER TAX\n");

        System.out.println("\t" + bank.getAccount(id) +"\n\n\n\n");
    }

}
