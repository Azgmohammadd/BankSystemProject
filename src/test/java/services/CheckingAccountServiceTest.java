package services;

import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.service.exception.InsufficientFundsException;
import com.java.banksystemproject.service.exception.InvalidTransactionException;
import com.java.banksystemproject.service.account.impl.CheckingAccountService;
import com.java.banksystemproject.service.impl.TransactionService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckingAccountServiceTest {
    private final CheckingAccountService service = new CheckingAccountService(new TransactionService());
    private CheckingAccount checkingAccount;

    @Before
    public void startup() {
        checkingAccount = CheckingAccount.builder().accountNumber("5859831180088659").accountHolderNumber("محمد ازقندی").balance(2000000).overdraftLimit(100000.0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositNegationValueTest() {
        service.deposit(checkingAccount, -20000);
    }

    @Test
    public void depositPositiveValueTest() {
        service.deposit(checkingAccount, 200000);
        assertEquals(checkingAccount.getBalance(), 2200000, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegationValueTest() throws InsufficientFundsException, InvalidTransactionException {
        service.withdraw(checkingAccount, -20000);
    }

    @Test(expected = InsufficientFundsException.class)
    public void withdrawToMuchValueTest() throws InsufficientFundsException, InvalidTransactionException {
        service.withdraw(checkingAccount, 200000000);
    }

    @Test(expected = InsufficientFundsException.class)
    public void withdrawOverDraftLimitTest() throws InsufficientFundsException, InvalidTransactionException {
        service.withdraw(checkingAccount, 2120000);
    }

    @Test
    public void withdrawUnderDraftLimitTest() throws InsufficientFundsException, InvalidTransactionException {
        service.withdraw(checkingAccount, 2020000);
        assertEquals(checkingAccount.getBalance(), -20000, 0);
    }

    @Test
    public void withdrawEnoughValueTest() throws InsufficientFundsException, InvalidTransactionException {
        service.withdraw(checkingAccount, 2000000);
        assertEquals(checkingAccount.getBalance(), 0, 0);
    }

    @Test
    public void getBalanceTest() {
        assertEquals(checkingAccount.getBalance(), 2000000, 0);
    }
}

