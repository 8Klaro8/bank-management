package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import src.main.model.account.Chequing;
import src.main.model.account.Loan;
import src.main.model.account.Savings;
import src.main.model.account.impl.Account;
import src.main.model.account.impl.Taxable;

public class AccountTests {

   Account[] accounts;


   @Before
   public void beforeTest() {
        accounts = new Account[]{
            new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51),
            new Savings("ce07d7b3-9038-43db-83ae-77fd9c0450c9", "Saul Goodman", 2241.60),
            new Loan("4991bf71-ae8f-4df9-81c1-9c79cff280a5", "Phoebe Buffay", 2537.31)
        };
   }

   @Test
   public void withdrawal() {
        accounts[0].withdraw(1440);
        assertEquals(84.51, accounts[0].getBalance());
   }

   @Test
   public void overdraft() {
        accounts[0].withdraw(1525.32);
        assertEquals(-6.31, accounts[0].getBalance());
   }

   @Test
   public void overdraftLimit() {
        accounts[0].withdraw(1724.51);
        assertEquals(-205.5, accounts[0].getBalance());
   }

   @Test
   public void savingsWithdrawal() {
        accounts[1].withdraw(2);
        assertEquals(2234.60, accounts[1].getBalance());
   }

   @Test
   public void loanWithdrawal() {
        accounts[2].withdraw(100);
        assertEquals(2639.31, accounts[2].getBalance());
   }

   @Test
   public void loanWithdrawLimit() {
        accounts[2].withdraw(7000);
        assertEquals(9677.31, accounts[2].getBalance());
   }
   
   @Test
   public void chequingDeposit() {
        accounts[0].deposit(3002);
        assertEquals(3002 + 1524.51 - 450.3, accounts[0].getBalance());
   }
   
   @Test
   public void loanDeposit() {
        accounts[2].deposit(100);
        assertEquals(2437.31, accounts[2].getBalance());
   }

   @Test
   public void taxTest() {
        // Chequing cqqqq = new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51);
        assertEquals(450.3, ((Taxable)accounts[0]).tax(3002));
   }


 

}
