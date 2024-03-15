package dao;

import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.TransactionDaoJDBC;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.service.account.impl.BankAccountService;
import com.java.banksystemproject.service.account.impl.SavingAccountService;
import com.java.banksystemproject.service.impl.TransactionService;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;
public class JDBCTest {

    TransactionService transactionService = new TransactionService();
    BankAccountDaoJDBC bankAccountDaoJDBC = new BankAccountDaoJDBC();
    TransactionDaoJDBC transactionDaoJDBC = new TransactionDaoJDBC();
    private static final BankAccountDaoJDBC dao = new BankAccountDaoJDBC();
    private static CheckingAccount checkingAccount;
    private static SavingAccount savingAccount;
    @BeforeClass
    public static void startup() {
        checkingAccount = CheckingAccount.builder()
                .accountNumber("1234567")
                .accountHolderNumber("MMD")
                .balance(2000000)
                .overdraftLimit(5000)
                .active(true)
                .build();

        dao.save(checkingAccount);
        System.out.println(dao.getAll());

        savingAccount = SavingAccount.builder()
                .accountNumber("9912321")
                .accountHolderNumber("AZG")
                .balance(2000000)
                .minimumBalanceInMonth(50000)
                .monthlyInterestRate(0.05f)
                .minimumBalance(40000)
                .active(false)
                .build();
        dao.save(savingAccount);
        System.out.println(dao.getAll());
    }

    @Test
    public void getCheckingAccountTest() {
        BankAccount account = dao.get(checkingAccount.getAccountNumber()).get();
        assertEquals(account.getAccountHolderNumber(), "MMD");
        assertEquals(account.getBalance(), 2000000, 0);
    }

    @Test
    public void getSavingAccountTest() {
        BankAccount account = dao.get(savingAccount.getAccountNumber()).get();
        assertEquals(account.getAccountHolderNumber(), "AZG");
        assertEquals(account.getBalance(), 2000000, 0);
    }

    @Test
    public void getAllTest() {
//        Collection<BankAccount> collection = dao.getAll();
//        assertEquals(collection.size(), 2);
//        assertTrue(collection.contains(savingAccount));
    }

    @Test
    public void deleteTest() {
        dao.delete(checkingAccount);
        assertEquals(dao.getAll().size(), 1);
    }

    @Test
    public void withdraw() {
        BankAccountService service = new SavingAccountService(transactionService, bankAccountDaoJDBC, transactionDaoJDBC);
        service.withdraw(savingAccount,10000);
        System.out.println(dao.getAll());
        System.out.println(new TransactionDaoJDBC().getAll());
    }



}
