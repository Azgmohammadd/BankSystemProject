package services;

import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.service.account.IBankAccountService;
import com.java.banksystemproject.service.account.factory.SavingAccountServiceFactory;
import com.java.banksystemproject.service.exception.InsufficientFundsException;
import com.java.banksystemproject.service.exception.InvalidTransactionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SavingAccountServiceTest {
    private static IBankAccountService bankAccountService;
    private static SavingAccount savingAccount;

    @BeforeAll
    public static void startup() {
        bankAccountService = new SavingAccountServiceFactory().getJDBC();
        savingAccount = SavingAccount.builder()
                .accountNumber("5859831180088659")
                .accountHolderNumber("محمد ازقندی")
                .balance(2000000)
                .minimumBalance(1000000)
                .build();

        bankAccountService.create(savingAccount);
    }

    @Test
    public void depositNegationValueTest() {
        assertThrows(IllegalArgumentException.class, () -> bankAccountService.deposit(savingAccount, -20000));
    }

    @Test
    public void depositPositiveValueTest() {
        bankAccountService.deposit(savingAccount, 200000);
        Optional<BankAccount> sa = bankAccountService.get(savingAccount);

        if (sa.isPresent()) {
            assertEquals(sa.get().getBalance(), 2200000, 0);
        } else {
            throw new RuntimeException("Saving account not found");
        }
    }

    @Test
    public void withdrawNegationValueTest() throws InsufficientFundsException, InvalidTransactionException {
        assertThrows(IllegalArgumentException.class, () -> bankAccountService.withdraw(savingAccount, -20000));
    }

    @Test
    public void withdrawToMuchValueTest() throws InsufficientFundsException, InvalidTransactionException {
        assertThrows(InvalidTransactionException.class, () -> bankAccountService.withdraw(savingAccount, 200000000));
    }

    @Test
    public void withdrawOverMinimumTest() throws InsufficientFundsException, InvalidTransactionException {
        assertThrows(InvalidTransactionException.class, () -> bankAccountService.withdraw(savingAccount, 1950000));
    }

    @Test
    public void withdrawUnderMinimumTest() throws InsufficientFundsException, InvalidTransactionException {
        bankAccountService.withdraw(savingAccount, 200000);
        Optional<BankAccount> sa = bankAccountService.get(savingAccount);

        if (sa.isPresent()) {
            assertEquals(sa.get().getBalance(), 1800000, 0);
        } else {
            throw new RuntimeException("Saving account not found");
        }

    }

    @Test
    @Order(1)
    public void getBalanceTest() {
        Optional<BankAccount> sa = bankAccountService.get(savingAccount);

        if (sa.isPresent()) {
            assertEquals(sa.get().getBalance(), 2000000, 0);
        } else {
            throw new RuntimeException("Saving account not found");
        }
    }
}

