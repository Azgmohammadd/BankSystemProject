package dao;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.dao.ITransactionDao;
import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.TransactionDaoJDBC;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.model.constant.TransactionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionDaoTest {
    private static ITransactionDao transactionDao;
    private static Transaction transaction;

    @BeforeAll
    public static void setUp() {
        transactionDao = new TransactionDaoJDBC();
        BankAccount sourceBankAccount = CheckingAccount.builder()
                .accountNumber("23321")
                .accountHolderNumber("test")
                .balance(1000)
                .overdraftLimit(200)
                .build();

        IBankAccountDao bankAccountDao = new BankAccountDaoJDBC();
        bankAccountDao.save(sourceBankAccount);

        transaction = Transaction.builder()
                .transactionType(TransactionType.ATM)
                .amount(1000)
                .transactionDate(new java.sql.Date(new Date().getTime()))
                .sourceAccountNumber(sourceBankAccount.getAccountNumber())
                .fee(100)
                .build();
    }

    @Test
    @Order(1)
    public void addTransactionTest() {
        transactionDao.save(transaction);
    }

    @Test
    @Order(2)
    public void getTransactionTest() {
        Optional<Transaction> t = transactionDao.get(transaction.getTransactionId());

        if (t.isPresent()) {
            assertEquals(transaction, t.get());
        } else {
            throw new RuntimeException("Transaction not found");

        }
    }

    @Test
    @Order(3)
    public void getAllTransactionsTest() {
        Collection<Transaction> transactions = transactionDao.getAll();

        assertEquals(transactions.size(), 1);
    }

    @Test
    @Order(4)
    public void deleteTransactionTest() {
        transactionDao.delete(transaction);
    }

}
