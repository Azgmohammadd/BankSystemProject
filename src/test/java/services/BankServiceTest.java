package services;

import com.java.banksystemproject.dao.impl.BankDao;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.service.account.IBankAccountService;
import com.java.banksystemproject.service.account.factory.SavingAccountServiceFactory;
import com.java.banksystemproject.service.impl.BankService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BankServiceTest {
    private static BankDao bank;

    @BeforeAll
    public static void startup() {
        IBankAccountService bankAccountService = new SavingAccountServiceFactory().getJDBC();
        bank = new BankDao();
        for (int i = 0; i < 1000; i++) {
            SavingAccount sa = SavingAccount.builder().accountNumber(String.valueOf(i)).accountHolderNumber(String.valueOf(i))
                    .balance(1000 + 10 * i).minimumBalanceInMonth(100).monthlyInterestRate(0.0001f).minimumBalance(10).build();

            bankAccountService.create(sa);
            bank.save(sa);
        }

    }

    @Test
    public void calculateBankTotalBalanceTest() {
        BigDecimal totalBalance = BankService.calculateBankTotalBalance(bank);
        assertEquals(5995000, totalBalance.longValue());
    }

    @Test
    public void applyInterestToAllAccountsTest() {
        BankService.applyInterestToAllAccounts(bank);
        BigDecimal totalBalance = BankService.calculateBankTotalBalance(bank);
        assertEquals(5995000, totalBalance.longValue());

    }

    @Test
    public void getSumOfHighValueBalancesTest() {
        BigDecimal totalBalance = BankService.getSumOfHighValueBalances(bank, 10000);
        assertEquals(1049500, totalBalance.longValue());
    }

    @Test
    public void filterSavingAccountsAndApplyInterestTest() {
        BankService.filterSavingAccountsAndApplyInterest(bank);
        BigDecimal totalBalance = BankService.calculateBankTotalBalance(bank);
        assertEquals(5995000, totalBalance.longValue());
    }
}
