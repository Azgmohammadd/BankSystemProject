package services;

import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.service.account.IBankAccountService;
import com.java.banksystemproject.service.account.factory.CheckingAccountServiceFactory;
import com.java.banksystemproject.service.exception.InsufficientFundsException;
import com.java.banksystemproject.service.exception.InvalidTransactionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CheckingAccountServiceTest {

    private static IBankAccountService bankAccountService;
    private static CheckingAccount checkingAccount;

    @BeforeAll
    public static void startup() {
        bankAccountService = new CheckingAccountServiceFactory().getJDBC();
        checkingAccount = CheckingAccount.builder()
                .accountNumber("5859831180088659")
                .accountHolderNumber("محمد ازقندی")
                .balance(2000000)
                .overdraftLimit(100000.0)
                .build();

        bankAccountService.create(checkingAccount);
    }

    @Test
    public void depositNegationValueTest() {
        assertThrows(IllegalArgumentException.class, () -> bankAccountService.deposit(checkingAccount, -20000));
    }

    @Test
    public void depositPositiveValueTest() {
        bankAccountService.deposit(checkingAccount, 200000);
        Optional<BankAccount> ch = bankAccountService.get(checkingAccount);

        if (ch.isPresent()) {
            assertEquals(ch.get().getBalance(), 2200000, 0);
        } else {
            throw new RuntimeException("Checking account not found");
        }
    }

    @Test
    public void withdrawNegationValueTest() throws InsufficientFundsException, InvalidTransactionException {
        assertThrows(IllegalArgumentException.class, () -> bankAccountService.withdraw(checkingAccount, -20000));
    }

    @Test
    public void withdrawToMuchValueTest() throws InsufficientFundsException, InvalidTransactionException {
        assertThrows(InsufficientFundsException.class, () -> bankAccountService.withdraw(checkingAccount, 200000000));
    }

    @Test
    public void withdrawOverDraftLimitTest() throws InsufficientFundsException, InvalidTransactionException {
        assertThrows(InsufficientFundsException.class, () -> bankAccountService.withdraw(checkingAccount, 2120000));
    }

    @Test
    public void withdrawUnderDraftLimitTest() throws InsufficientFundsException, InvalidTransactionException {
        bankAccountService.withdraw(checkingAccount, 2020000);
        Optional<BankAccount> ch = bankAccountService.get(checkingAccount);

        if (ch.isPresent()) {
            assertEquals(ch.get().getBalance(), -20000, 0);
        } else {
            throw new RuntimeException("Checking account not found");
        }
    }

    @Test
    public void withdrawEnoughValueTest() throws InsufficientFundsException, InvalidTransactionException {
        bankAccountService.withdraw(checkingAccount, 2000000);
        Optional<BankAccount> ch = bankAccountService.get(checkingAccount);

        if (ch.isPresent()) {
            assertEquals(ch.get().getBalance(), 0, 0);
        } else {
            throw new RuntimeException("Checking account not found");
        }
    }

    @Test
    public void getBalanceTest() {
        Optional<BankAccount> ch = bankAccountService.get(checkingAccount);

        if (ch.isPresent()) {
            assertEquals(ch.get().getBalance(), 2000000, 0);
        } else {
            throw new RuntimeException("Checking account not found");
        }
    }
}

