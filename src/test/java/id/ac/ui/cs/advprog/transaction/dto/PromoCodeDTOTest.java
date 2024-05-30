package id.ac.ui.cs.advprog.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PromoCodeDTOTest {

    PromoCodeDTO promoCodeDTO;
    DateTimeFormatter newDateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");

    @BeforeEach
    void setUp() {
        promoCodeDTO = new PromoCodeDTO();
        promoCodeDTO.setId(UUID.fromString("7af4edd5-97b8-49cc-8b75-4d4a9d0029ef"));
        promoCodeDTO.setName("BELANJAHEMAT25");
        promoCodeDTO.setDescription("diskon 25%");
        promoCodeDTO.setExpiredDate(LocalDate.parse("10 08 2024", newDateFormatter));
        promoCodeDTO.setMinimumPurchase(100000.0);
    }

    @Test
    void testGetId() {
        assertEquals(UUID.fromString("7af4edd5-97b8-49cc-8b75-4d4a9d0029ef"), this.promoCodeDTO.getId());
    }

    @Test
    void testGetName() {
        assertEquals("BELANJAHEMAT25", this.promoCodeDTO.getName());
    }

    @Test
    void testGetDescription() {
        assertEquals("diskon 25%", this.promoCodeDTO.getDescription());
    }

    @Test
    void testGetExpiredDate() {
        assertEquals(LocalDate.parse("10 08 2024", newDateFormatter), this.promoCodeDTO.getExpiredDate());
    }

    @Test
    void testMinimumPurchase() {
        assertEquals(100000.0, this.promoCodeDTO.getMinimumPurchase());
    }
}