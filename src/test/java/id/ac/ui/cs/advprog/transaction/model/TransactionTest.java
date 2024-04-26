package id.ac.ui.cs.advprog.transaction.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    Transaction transaction;
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    @BeforeEach
    void setUp() {
        this.transaction = new transaction("dummyUsername");
        this.transaction.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        this.transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        this.transaction.setProductAmount(15);
        this.transaction.setPromoCode(null);

        try {
            this.transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.transaction.setTotalPrice(34500000.0);
    }

    @Test
    void testGetUserUsername() {
        assertEquals("dummyUsername", this.transaction.getUserUsername());
    }

    @Test
    void testGetTransactionId() {
        assertEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"), this.transaction.getId());
    }

    @Test
    void testGetProductId() {
        assertEquals(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"), this.transaction.getProductId());
    }

    @Test
    void testGetProductAmount() {
        assertEquals(15, this.transaction.getProductAmount());
    }

    @Test
    void testGetPromoCode() {
        assertNull(this.transaction.getPromoCode());
    }

    @Test
    void testGetPaymentDate() {
        assertEquals("Mon Apr 22 18:58:25 WIB 2024", this.dateFormat.parse(this.transaction.getPaymentDate));
    }

    @Test
    void testGetTotalPrice() {
        assertEquals(34500000.0, this.transaction.getTotalPrice);
    }
}