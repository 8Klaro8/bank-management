package src.main.model;

import java.net.Socket;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Transaction implements Comparable<Transaction> {

    private String id;
    private double amount;
    private long timestamp;

    public enum TransactionType {
        WITHDRAW, DEPOSIT
    }

    public TransactionType transaction;

    // constr.
    public Transaction(long timestamp, TransactionType transaction, String id,  double amount) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID can't be null or blank.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount can't be negative.");
        }
        this.id = id;
        this.amount = amount;
        this.timestamp = timestamp;
        this.transaction = transaction;
    }

    // copy constr.
    public Transaction(Transaction source) {
        this.id = source.id;
        this.amount = source.amount;
        this.timestamp = source.timestamp;
        this.transaction = source.transaction;
    }

    // get set
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID can't be null or blank.");
        }
        this.id = id;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount can't be negative.");
        }
        this.amount = amount;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionType getTransaction() {
        return this.transaction;
    }

    public void setTransaction(TransactionType transaction) {
        this.transaction = transaction;
    }

        
    // returnDate
    public String returnDate() {
        Date newDate = new Date(this.timestamp * 1000);
        return new SimpleDateFormat("dd-MM-yyyy").format(newDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Transaction)) {
            return false;
        }
        Transaction transaction = (Transaction) obj;
        return this.amount == transaction.amount &&
                this.id.equals(transaction.id) &&
                this.timestamp == transaction.timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, timestamp);
    }

    // comaoteTo
    @Override
    public int compareTo(Transaction o) {
        // TODO Auto-generated method stub
        return Double.compare(this.timestamp, o.timestamp);
    }


    @Override
    public String toString() {
        return "\t" + this.transaction + "" +
                "\t" + returnDate() + "" +
                "\t" + id + "" +
                "\t$" + amount + "";
    }

}
