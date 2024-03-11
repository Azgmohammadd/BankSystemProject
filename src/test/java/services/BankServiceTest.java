package services;

import com.java.banksystemproject.dao.impl.BankDao;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.service.impl.BankService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BankServiceTest {
    private BankDao bank;

    @Before
    public void startup() {
        bank = new BankDao();
        for (int i = 0; i < 1000; i++)
            bank.save(SavingAccount.builder().accountNumber(String.valueOf(i)).accountHolderNumber(String.valueOf(i))
                    .balance(1000 + 10 * i).minimumBalanceInMonth(100).MonthlyInterestRate(0.0001f).MINIMUM_BALANCE(10).build());
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
        assertEquals(6095010, totalBalance.longValue());
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
        assertEquals(6095010, totalBalance.longValue());
    }
}
