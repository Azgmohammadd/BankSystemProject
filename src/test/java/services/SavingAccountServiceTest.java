package services;

import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.service.exception.InsufficientFundsException;
import com.java.banksystemproject.service.exception.InvalidTransactionException;
import com.java.banksystemproject.service.account.impl.SavingAccountService;
import com.java.banksystemproject.service.impl.TransactionService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingAccountServiceTest {
    private final SavingAccountService service = new SavingAccountService(new TransactionService());
    private SavingAccount savingAccount;

    @Before
    public void startup() {
        savingAccount = SavingAccount.builder().accountNumber("5859831180088659").accountHolderNumber("محمد ازقندی").balance(2000000).MINIMUM_BALANCE(1000000).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositNegationValueTest() {
        service.deposit(savingAccount, -20000);
    }

    @Test
    public void depositPositiveValueTest() {
        service.deposit(savingAccount, 200000);
        assertEquals(savingAccount.getBalance(), 2200000, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegationValueTest() throws InsufficientFundsException, InvalidTransactionException {
        service.withdraw(savingAccount, -20000);
    }

    @Test(expected = InvalidTransactionException.class)
    public void withdrawToMuchValueTest() throws InsufficientFundsException, InvalidTransactionException {
        service.withdraw(savingAccount, 200000000);
    }

    @Test(expected = InvalidTransactionException.class)
    public void withdrawOverMinimumTest() throws InsufficientFundsException, InvalidTransactionException {
        service.withdraw(savingAccount, 1950000);
    }

    @Test
    public void withdrawUnderMinimumTest() throws InsufficientFundsException, InvalidTransactionException {
        service.withdraw(savingAccount, 200000);
        assertEquals(savingAccount.getBalance(), 1800000, 0);
    }

    @Test
    public void getBalanceTest() {
        assertEquals(savingAccount.getBalance(), 2000000, 0);
    }

}

