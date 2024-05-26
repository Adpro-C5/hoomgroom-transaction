package id.ac.ui.cs.advprog.transaction.service;

import id.ac.ui.cs.advprog.transaction.handler.TotalPriceHandler;
import id.ac.ui.cs.advprog.transaction.model.Transaction;
import id.ac.ui.cs.advprog.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest{

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TotalPriceHandler totalPriceHandler;

    SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp(){
        // MockitoAnnotations.openMocks(this);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    @Test
    void testCreateAndFind(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.fromString("5814d7e4-4fa9-4cb3-80da-4b7315792611"));
        transaction.setUserId(1);
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);
        transaction.setTotalPrice(34500000.0);

        try {
            // transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
            transaction.setPaymentDate(this.dateFormat.parse("2024-04-22T18:58:25"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        transactionService.create(transaction);

        when(transactionRepository.findAll()).thenReturn(List.of(transaction));
        List<Transaction> transactionList = transactionService.findAll();
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
    void testFindByUserId(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.fromString("5814d7e4-4fa9-4cb3-80da-4b7315792611"));
        transaction.setUserId(1);
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);
        transaction.setTotalPrice(34500000.0);

        try {
            transaction.setPaymentDate(this.dateFormat.parse("2024-04-22T18:58:25"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId(UUID.fromString("62be915c-251f-4d01-b6de-ec353d57edc6"));
        transaction2.setUserId(1);
        transaction2.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction2.setProductAmount(20);
        transaction2.setPromoCode(null);
        transaction2.setTotalPrice(55600000.0);

        try {
            transaction2.setPaymentDate(this.dateFormat.parse("2024-05-32T19:59:26"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        when(transactionRepository.findAllByUserId(1)).thenReturn(List.of(transaction, transaction2));

        List<Transaction> transactionList = transactionService.findAllByUserId(1);

        assertEquals(transactionList.get(0).getUserId(), transaction.getUserId());
        assertEquals(transactionList.get(1).getUserId(), transaction.getUserId());
    }

    @Test
    void testFindById(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.fromString("5814d7e4-4fa9-4cb3-80da-4b7315792611"));
        transaction.setUserId(1);
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);
        transaction.setTotalPrice(34500000.0);

        try {
            transaction.setPaymentDate(this.dateFormat.parse("2024-04-22T18:58:25"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        when(transactionRepository.findById(UUID.fromString("5814d7e4-4fa9-4cb3-80da-4b7315792611"))).thenReturn(Optional.of(transaction));

        Transaction foundTransaction = transactionService.findById(UUID.fromString("5814d7e4-4fa9-4cb3-80da-4b7315792611")).get();

        assertEquals(transaction.getTransactionId(), foundTransaction.getTransactionId());
    }

    @Test
    void testEditTransaction(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.fromString("5814d7e4-4fa9-4cb3-80da-4b7315792611"));
        transaction.setUserId(1);
        transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction.setProductAmount(15);
        transaction.setPromoCode(null);
        transaction.setTotalPrice(34500000.0);

        try {
            transaction.setPaymentDate(this.dateFormat.parse("2024-04-22T18:58:25"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Transaction editedTransaction = new Transaction();
        editedTransaction.setTransactionId(UUID.fromString("5814d7e4-4fa9-4cb3-80da-4b7315792611"));
        editedTransaction.setUserId(1);
        editedTransaction.setProductId(UUID.fromString("8dff20a8-9df2-4601-b301-30b31737444c"));
        editedTransaction.setProductAmount(15);
        editedTransaction.setPromoCode("BELANJAHEMAT20");
        editedTransaction.setTotalPrice(7975000.0);

        try {
            editedTransaction.setPaymentDate(this.dateFormat.parse("2024-04-22T18:58:25"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        when(transactionRepository.findById(UUID.fromString("5814d7e4-4fa9-4cb3-80da-4b7315792611"))).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction resultTransaction = transactionService.edit(editedTransaction);

        assertEquals(transaction.getTransactionId(), resultTransaction.getTransactionId());
        assertEquals(transaction.getUserId(), resultTransaction.getUserId());
        assertEquals(transaction.getProductId(), resultTransaction.getProductId());
        assertEquals(transaction.getProductAmount(), resultTransaction.getProductAmount());
        assertEquals(transaction.getPromoCode(), resultTransaction.getPromoCode());
        assertEquals(transaction.getTotalPrice(), resultTransaction.getTotalPrice());
        assertEquals(transaction.getPaymentDate(), resultTransaction.getPaymentDate());
    }
}