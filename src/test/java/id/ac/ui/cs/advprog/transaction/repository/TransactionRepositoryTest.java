package id.ac.ui.cs.advprog.transaction.repository;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest{

    SimpleDateFormat dateFormat;
    @InjectMocks
    TransactionRepository transactionRepository;

    @BeforeEach
    void setUp(){
        this.dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    }

    @Test
    void testCreateAndFind(){
        Transaction transaction = new Transaction("dummyUsername");
        transaction.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);

        try {
            transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        transaction.setTotalPrice(34500000.0);

        transactionRepository.create(transaction);

        Iterator<Transaction> transactionIterator = transactionRepository.findAll();
        assertTrue(transactionRepository.hasNext());
        Transaction savedTransaction = transactionIterator.next();
        assertEquals(transaction.getId(), savedTransaction.getId());
        assertEquals(transaction.getUserUsername(), savedTransaction.getUserUsername());
        assertEquals(transaction.getProductId(), savedTransaction.getProductId());
        assertEquals(transaction.getProductAmount(), savedTransaction.getProductAmount());
        assertEquals(transaction.getPromoCode(), savedTransaction.getPromoCode());
        assertEquals(transaction.getPaymentDate(), savedTransaction.getPaymentDate());
        assertEquals(transaction.getTotalPrice(), savedTransaction.getTotalPrice());
    }

    @Test
    void testFindAllIfEmpty(){
        Iterator<Transaction> transactionIterator = transactionRepository.findAll();
        assertFalse(transactionIterator.hasNext());
    }

    @Test
    void testEditTransaction(){
        Transaction transaction = new Transaction("dummyUsername");
        transaction.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);

        try {
            transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        transaction.setTotalPrice(34500000.0);

        transactionRepository.create(transaction);

        Transaction editedTransaction1 = new Transaction(transaction.getUserUsername());
        editedTransaction1.setId(transaction.getId());
        editedTransaction1.setProductId(UUID.fromString("035ee4bb-9510-4444-aeea-cec565e91d3c"));
        editedTransaction1.setProductAmount(20);
        editedTransaction1.setPromoCode("BELANJAHEMAT20");
        editedTransaction1.setTotalPrice(5500000.0);
        transactionRepository.edit(editedTransaction1);

        assertEquals(transaction.getId(), editedTransaction1.getId());
        assertEquals(transaction.getUserUsername(), editedTransaction1.getUserUsername());
        assertEquals(UUID.fromString("035ee4bb-9510-4444-aeea-cec565e91d3c"), editedTransaction1.getProductId());
        assertEquals(20, editedTransaction1.getProductAmount());
        assertEquals("BELANJAHEMAT20", editedTransaction1.getPromoCode());
        assertEquals(transaction.getPaymentDate(), editedTransaction1.getPaymentDate());
        assertEquals(5500000.0, editedTransaction1.getTotalPrice());
    }

    @Test
    void testEditTransactionNotFound(){
        Transaction transaction = new Transaction("dummyUsername");
        transaction.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);

        try {
            transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        transaction.setTotalPrice(34500000.0);

        transactionRepository.create(transaction);

        Transaction editedTransaction1 = new Transaction(transaction.getUserUsername());
        editedTransaction1.setId(UUID.fromString("b07472cf-4dd0-45e7-84c6-01820af7daad")); // set wrong ID
        editedTransaction1.setProductId(UUID.fromString("035ee4bb-9510-4444-aeea-cec565e91d3c"));
        editedTransaction1.setProductAmount(20);
        editedTransaction1.setPromoCode("BELANJAHEMAT20");
        editedTransaction1.setTotalPrice(5500000.0);

        assertThrows(NoSuchElementException.class, () -> {
            transactionRepository.edit(editedTransaction1);
        });
    }

    @Test
    void testDeleteTransactionThenFindById(){
        Transaction transaction = new Transaction("dummyUsername");
        transaction.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);

        try {
            transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        transaction.setTotalPrice(34500000.0);

        transactionRepository.create(transaction);

        transactionRepository.delete(transaction.getId());
        UUID id1 = transaction.getId();

        assertThrows(NoSuchElementException.class, () -> {
            transaction.findById(id1);
        });
    }
}