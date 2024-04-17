package dao;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountDaoTest {
    private static BankAccount savingAccount;
    private static BankAccount checkingAccount;
    private static IBankAccountDao dao;

    @BeforeAll
    @Order(1)
    public static void startup(){
        savingAccount = SavingAccount.builder()
                .accountNumber("123458")
                .accountHolderNumber("Ali3")
                .balance(5000d)
                .minimumBalanceInMonth(1000)
                .minimumBalance(500)
                .monthlyInterestRate(0.01d)
                .build();

        checkingAccount = CheckingAccount.builder()
                .accountNumber("12345566")
                .accountHolderNumber("AHN123")
                .balance(5000d)
                .overdraftLimit(100)
                .build();

        dao = new BankAccountDaoJDBC();
    }

    @BeforeAll
    @Order(2)
    public static void saveAccounts() {
        dao.save(savingAccount);
        dao.save(checkingAccount);
    }

    @Test
    public void getCheckingAccountTest(){
        Optional<BankAccount> account = dao.get(checkingAccount.getAccountNumber());
        assertFalse(account.isEmpty());
    }

    @Test
    public void getSavingAccountTest(){
        Optional<BankAccount> account = dao.get(savingAccount.getAccountNumber());
        assertFalse(account.isEmpty());
    }

    @Test
    public void getAllAccountsTest() {
        Collection<BankAccount> accounts = dao.getAll();
        assertFalse(accounts.isEmpty());
        assertEquals(accounts.size(), 2);
    }

    @Test
    public void updateBalanceTest() {
        dao.updateBalance(checkingAccount, 2500);

        Optional<BankAccount> account = dao.get(checkingAccount.getAccountNumber());

        if (account.isPresent()) {
            assertEquals(account.get().getBalance(), 2500);
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    @Test
    public void updateMinimumBalanceTest() {
        dao.updateMinimumBalance((SavingAccount) savingAccount, 1000);

        Optional<BankAccount> account = dao.get(savingAccount.getAccountNumber());

        if (account.isPresent()) {
            SavingAccount sa = (SavingAccount) account.get();

            assertEquals(sa.getMinimumBalance(), 1000);
        }else {
            throw new RuntimeException("Account not found");
        }
    }

    @Test
    public void testDelete(){
        dao.delete(savingAccount);
    }
}