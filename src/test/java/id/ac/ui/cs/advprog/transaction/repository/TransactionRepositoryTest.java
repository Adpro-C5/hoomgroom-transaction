package id.ac.ui.cs.advprog.transaction.repository;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class TransactionRepositoryTest{

    SimpleDateFormat dateFormat;

    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    void setUp(){
        // transactionRepository = Mockito.mock(TransactionRepository.class);
        this.dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    }

    @Test
    void testCreateAndFind(){
        Transaction transaction = new Transaction();
        transaction.setUserId(1);
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);
        transaction.setTotalPrice(34500000.0);

        transactionRepository.save(transaction);

        List<Transaction> transactionList = transactionRepository.findAll();
        Transaction savedTransaction = transactionList.getFirst();

        assertNotEquals(null, savedTransaction.getTransactionId());
        assertEquals(transaction.getUserId(), savedTransaction.getUserId());
        assertEquals(transaction.getProductId(), savedTransaction.getProductId());
        assertEquals(transaction.getProductAmount(), savedTransaction.getProductAmount());
        assertEquals(transaction.getPromoCode(), savedTransaction.getPromoCode());
        assertNotEquals(null, savedTransaction.getPaymentDate());
        assertEquals(transaction.getTotalPrice(), savedTransaction.getTotalPrice());
    }

    @Test
    void testFindAllIfEmpty(){
        List<Transaction> transactionList = transactionRepository.findAll();
        assertTrue(transactionList.isEmpty());
    }

    @Test
    void testEditTransaction(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        transaction.setUserId(1);
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);

        try {
            transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        transaction.setTotalPrice(34500000.0);

        transactionRepository.save(transaction);

        Transaction tempTransaction = transactionRepository.findAll().getFirst();
        tempTransaction.setProductId(UUID.fromString("035ee4bb-9510-4444-aeea-cec565e91d3c"));
        tempTransaction.setProductAmount(20);
        tempTransaction.setPromoCode("BELANJAHEMAT20");
        tempTransaction.setTotalPrice(5500000.0);
        transactionRepository.save(tempTransaction);

        Transaction editedTransaction = transactionRepository.findAll().getFirst();

        assertEquals(transaction.getUserId(), editedTransaction.getUserId());
        assertEquals(UUID.fromString("035ee4bb-9510-4444-aeea-cec565e91d3c"), editedTransaction.getProductId());
        assertEquals(20, editedTransaction.getProductAmount());
        assertEquals("BELANJAHEMAT20", editedTransaction.getPromoCode());
        assertEquals(tempTransaction.getPaymentDate(), editedTransaction.getPaymentDate());
        assertEquals(5500000.0, editedTransaction.getTotalPrice());
    }

    @Test
    void testFindById(){
        Transaction transaction = new Transaction();
        transaction.setUserId(1);
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);
        transaction.setTotalPrice(34500000.0);

        transactionRepository.save(transaction);

        Transaction tempTransaction = transactionRepository.findAll().getFirst();
        UUID tempTransactionId = tempTransaction.getTransactionId();

        Transaction foundTransaction = transactionRepository.findById(tempTransactionId).get();
        assertEquals(transaction.getUserId(), foundTransaction.getUserId());
        assertEquals(transaction.getProductId(), foundTransaction.getProductId());
        assertEquals(transaction.getProductAmount(), foundTransaction.getProductAmount());
        assertEquals(transaction.getPromoCode(), foundTransaction.getPromoCode());
        assertEquals(transaction.getTotalPrice(), foundTransaction.getTotalPrice());
    }

    @Test
    void testDeleteTransactionThenFindById(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        transaction.setUserId(1);
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);

        try {
            transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        transaction.setTotalPrice(34500000.0);

        transactionRepository.save(transaction);

        Transaction tempTransaction = transactionRepository.findAll().getFirst();
        UUID tempTransactionId = tempTransaction.getTransactionId();

        transactionRepository.deleteById(tempTransactionId);

        assertEquals(Optional.empty(), transactionRepository.findById(tempTransactionId));
    }
}