package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.dto.PromoCodeDTO;
import id.ac.ui.cs.advprog.transaction.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromoCodeHandlerTest {

    @InjectMocks
    PromoCodeHandler promoCodeHandler;

    @Mock
    RestTemplate restTemplate;

    Transaction transaction;

    PromoCodeDTO promoCodeDTO;

    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    DateTimeFormatter newDateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");

    @BeforeEach
    void setUp() {
        this.transaction = new Transaction();
        this.transaction.setTransactionId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        this.transaction.setUserId(1);
        this.transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        this.transaction.setProductAmount(15);
        this.transaction.setPromoCode("BELANJAHEMAT25");
        this.transaction.setTotalPrice(350000.0);

        try {
            this.transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        promoCodeDTO = new PromoCodeDTO();
        promoCodeDTO.setName("BELANJAHEMAT25");
        promoCodeDTO.setExpiredDate(LocalDate.parse("10 08 2024", newDateFormatter));
        promoCodeDTO.setMinimumPurchase(10000.0);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandle() {
        ResponseEntity<PromoCodeDTO> responseEntity = new ResponseEntity<>(promoCodeDTO, HttpStatus.OK);

        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.eq(PromoCodeDTO.class))).thenReturn(responseEntity);

        promoCodeHandler.handle(transaction, "jwtToken");

        assertEquals(262500.0, transaction.getTotalPrice());
    }
}