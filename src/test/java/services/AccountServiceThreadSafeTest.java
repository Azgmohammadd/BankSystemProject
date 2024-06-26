package services;

import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.TransactionDaoJDBC;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.service.account.impl.CheckingAccountService;
import com.java.banksystemproject.service.account.impl.SavingAccountService;
import com.java.banksystemproject.service.impl.TransactionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AccountServiceThreadSafeTest {
    TransactionService transactionService = new TransactionService(new TransactionDaoJDBC());
    BankAccountDaoJDBC bankAccountDaoJDBC = new BankAccountDaoJDBC();
    TransactionDaoJDBC transactionDaoJDBC = new TransactionDaoJDBC();
    private final CheckingAccountService checkingAccountService = new CheckingAccountService(transactionService, bankAccountDaoJDBC, transactionDaoJDBC);
    private final SavingAccountService savingAccountService = new SavingAccountService(transactionService, bankAccountDaoJDBC, transactionDaoJDBC);
    private static CheckingAccount checkingAccount;
    private static SavingAccount savingAccount;

    @BeforeAll
    public static void startup() {
        checkingAccount = CheckingAccount.builder().accountNumber("5859831180088659").accountHolderNumber("محمد ازقندی")
                .balance(2000000).overdraftLimit(5000000.0).build();
        savingAccount = SavingAccount.builder().accountNumber("5859831180088659").accountHolderNumber("محمد ازقندی").
                balance(20000).minimumBalance(100).minimumBalanceInMonth(100d).build();
    }

    @Test()
    public void checkingAccountThreadSafeTest() {
        // Create a thread for depositing
        Thread depositThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++)
                checkingAccountService.deposit(checkingAccount, 1000);
        });

        // Create a thread for withdrawing
        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++)
                checkingAccountService.withdraw(checkingAccount, 100);
        });

        // Start the threads
        depositThread.start();
        withdrawThread.start();

        // Wait for both threads to finish
        try {
            depositThread.join();
            withdrawThread.join();
        } catch (Exception ignored) {
        }

        assertEquals(2000000, checkingAccount.getBalance(), 0);
    }

    @Test()
    public void checkingSavingThreadSafeTest() {
        // Create a thread for apply interest
        Thread applyInterestThread = new Thread(() -> {
            for (int i = 0; i < 10; i++)
                savingAccountService.applyInterest(savingAccount);
        });

        // Create a thread for withdrawing
        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 10; i++)
                savingAccountService.withdraw(savingAccount, 1000000d);
        });

        // Start the threads
        applyInterestThread.start();
        withdrawThread.start();

        // Wait for both threads to finish
        try {
            applyInterestThread.join();
            withdrawThread.join();
        } catch (Exception ignored) {
        }
        assertEquals(20000, savingAccount.getBalance(), 1);
    }
}
