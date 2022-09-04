package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import src.main.model.Bank;
import src.main.model.Transaction;
import src.main.model.account.Chequing;

public class BankTests {

    Bank bank;

    @Before
    public void setup() {
        bank = new Bank();
        bank.addAccount(new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51));
    }

    @Test
    public void successfulTransaction() {
        this.bank.executeTransaction((new Transaction(1546905600, Transaction.TransactionType.WITHDRAW, "f84c43f4-a634-4c57-a644-7602f8840870", 624.99)));
        this.bank.executeTransaction((new Transaction(1578700800, Transaction.TransactionType.DEPOSIT, "f84c43f4-a634-4c57-a644-7602f8840870", 441.93)));

        assertEquals(2, bank.getTransactions("f84c43f4-a634-4c57-a644-7602f8840870").length);
    }

    @Test
    public void failedTransaction() {
        this.bank.executeTransaction((new Transaction(1546905600, Transaction.TransactionType.WITHDRAW, "f84c43f4-a634-4c57-a644-7602f8840870", 10000000)));
        assertEquals(0, bank.getTransactions("f84c43f4-a634-4c57-a644-7602f8840870").length);
    }

    @Test
    public void taxDeduction() {
        bank.executeTransaction(new Transaction( 1578700800, Transaction.TransactionType.DEPOSIT, "f84c43f4-a634-4c57-a644-7602f8840870", 4000));
        bank.executeTransaction(new Transaction( 1578700800, Transaction.TransactionType.WITHDRAW, "f84c43f4-a634-4c57-a644-7602f8840870", 500));
        // bank.deductTaxes();
        assertEquals(4424.51, bank.getAccount("f84c43f4-a634-4c57-a644-7602f8840870").getBalance());

        // 1524.51
    }
}
