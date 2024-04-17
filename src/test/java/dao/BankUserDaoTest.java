package dao;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.dao.IBankUserDao;
import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.BankUserDaoJDBC;
import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.model.constant.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankUserDaoTest {
    private static IBankUserDao bankUserDao;
    private static IBankAccountDao bankAccountDao;
    private static BankUser bankUser;

    @BeforeAll
    @Order(1)
    public static void setUp() {
        bankUserDao = new BankUserDaoJDBC();
        bankAccountDao = new BankAccountDaoJDBC();
        bankUser = BankUser.builder()
                .username("admin")
                .password("admin")
                .role(Role.ADMIN)
                .firstName("firstName")
                .lastName("lastName")
                .nationalCode("1234")
                .bankAccountsId(new HashSet<>())
                .build();
    }

    @BeforeAll
    @Order(2)
    public static void test2() {
        bankUserDao.save(bankUser);
    }

    @Test
    public void getBankUserTest() {
        Optional<BankUser> user = bankUserDao.get(bankUser.getUsername());

        if (user.isPresent()) {
            assertEquals(user.get(), bankUser);
        }else {
            throw new RuntimeException("User not found");
        }
    }

    @Test
    public void getAllBankUserTest() {
        List<BankUser> users = bankUserDao.getAll();

        assertEquals(users.size(), 1);
    }

    @Test
    public void addAccountTest() {
        BankAccount account = CheckingAccount.builder()
                .accountNumber("BankUserDaoTest")
                .accountHolderNumber("123H")
                .balance(12321d)
                .overdraftLimit(1000d)
                .build();

        bankAccountDao.save(account);
        bankUser.setBankAccountsId(Set.of(account.getAccountNumber()));
        bankUserDao.addAccount(bankUser, account);

        Optional<BankUser> user = bankUserDao.get(bankUser.getUsername());

        if (user.isPresent()) {
            assertEquals(user.get().getBankAccountsId(), bankUser.getBankAccountsId());
        }else {
            throw new RuntimeException("User not found");
        }
    }


}
