package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.xml.crypto.Data;

import org.junit.Before;
import org.junit.Test;

import src.main.model.Transaction;
import src.main.model.Transaction.TransactionType;

public class TransactionTest {
    
    Transaction transaction;

    @Before
    public void setup() {
        transaction = new Transaction(1546819200, Transaction.TransactionType.WITHDRAW, "ce07d7b3-9038-43db-83ae-77fd9c0450c9", 612.52);
    }

    @Test
    public void correctDataTest() {
        assertEquals("01-09-2022", transaction.returnDate());
    }
}
