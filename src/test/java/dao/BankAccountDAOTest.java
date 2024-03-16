package dao;

import com.java.banksystemproject.dao.impl.BankAccountDAO;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;

public class BankAccountDAOTest {
    private SavingAccount bankAccount;
    private BankAccountDAO dao;

    @Before
    public void startup(){
        bankAccount = SavingAccount.builder().accountNumber("123458").accountHolderNumber("Ali3").balance(5000d)
                .minimumBalanceInMonth(1000).MINIMUM_BALANCE(500).MonthlyInterestRate(0.01d).build();
        dao = new BankAccountDAO();
    }

    @Test
    public void testSave() {
        dao.save(bankAccount);
    }

    @Test
    public void testGet(){
        Optional<BankAccount> account = dao.get(bankAccount.getAccountNumber());
        assertFalse(account.isEmpty());
    }

    @Test
    public void testDelete(){
        dao.delete(bankAccount);
    }
}