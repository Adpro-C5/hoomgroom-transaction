package id.ac.ui.cs.advprog.transaction.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BalanceDecreaseDTOTest {

    BalanceDecreaseDTO balanceDecreaseDTO;

    @BeforeEach
    void setUp() {
        balanceDecreaseDTO = new BalanceDecreaseDTO(1, 0);
    }

    @Test
    void testGetUserId() {
        assertEquals(1, this.balanceDecreaseDTO.getUserId());
    }

    @Test
    void testGetAddedBalance() {
        assertEquals(0, this.balanceDecreaseDTO.getAddedBalance());
    }

    @Test
    void testSetUserId() {
        balanceDecreaseDTO.setUserId(2);
        assertEquals(2, this.balanceDecreaseDTO.getUserId());
    }

    @Test
    void testSetAddedBalance() {
        balanceDecreaseDTO.setAddedBalance(10000);
        assertEquals(10000, this.balanceDecreaseDTO.getAddedBalance());
    }
}