package dao;

import com.java.banksystemproject.dao.impl.BankAccountDAO;
import com.java.banksystemproject.model.account.BankAccount;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import static org.junit.Assert.*;

public class BankAccountDAOTest {
    private BankAccount bankAccount;
    private BankAccountDAO dao;

    @Before
    public void startup(){
        bankAccount = BankAccount.builder().accountNumber("123456").accountHolderNumber("Ali").balance(5000d).build();
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