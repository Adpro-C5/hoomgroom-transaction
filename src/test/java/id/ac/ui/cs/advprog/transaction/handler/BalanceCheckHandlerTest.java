package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.auth.AuthHelper;
import id.ac.ui.cs.advprog.transaction.dto.ProfileDTO;
import id.ac.ui.cs.advprog.transaction.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceCheckHandlerTest {

    @InjectMocks
    BalanceCheckHandler balanceCheckHandler;

    @Mock
    RestTemplate restTemplate;

    @Mock
    AuthHelper authHelper;

    Transaction transaction;

    ProfileDTO profileDTO;

    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    DateTimeFormatter newDateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");

    @BeforeEach
    void setUp() {
        this.transaction = new Transaction();
        this.transaction.setTransactionId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        this.transaction.setUserId(1);
        this.transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        this.transaction.setProductAmount(15);
        this.transaction.setPromoCode(null);
        this.transaction.setTotalPrice(350000.0);

        try {
            this.transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        profileDTO = new ProfileDTO("dummy-message", 1, null, null, null, null, null, null, (long)500000);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandle() {

        when(authHelper.getUserProfile("jwtToken")).thenReturn(profileDTO);

        ResponseEntity<ProfileDTO> responseEntity = new ResponseEntity<>(profileDTO, HttpStatus.OK);

        doReturn(responseEntity).when(restTemplate).exchange(
                eq("http://34.143.253.15/profile/balance/reduce"),
                eq(HttpMethod.PUT),
                ArgumentMatchers.<HttpEntity<?>>any(),
                eq(ProfileDTO.class)
        );

        balanceCheckHandler.handle(transaction, "jwtToken");

        assertEquals(350000.0, transaction.getTotalPrice());
    }
}