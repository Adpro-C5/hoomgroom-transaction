package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.dto.ProductDTO;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TotalPriceHandlerTest {

    @InjectMocks
    TotalPriceHandler totalPriceHandler;

    @Mock
    RestTemplate restTemplate;

    Transaction transaction;

    ProductDTO productDTO;

    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    @BeforeEach
    void setUp() {
        this.transaction = new Transaction();
        this.transaction.setTransactionId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        this.transaction.setUserId(1);
        this.transaction.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        this.transaction.setProductAmount(15);
        this.transaction.setPromoCode(null);

        try {
            this.transaction.setPaymentDate(this.dateFormat.parse("Mon Apr 22 18:58:25 WIB 2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.productDTO = new ProductDTO();
        productDTO.setPrice(100000.0);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandle() {
        ResponseEntity<ProductDTO> responseEntity = new ResponseEntity<>(productDTO, HttpStatus.OK);

        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.eq(ProductDTO.class))).thenReturn(responseEntity);

        totalPriceHandler.handle(transaction, "jwtToken");

        assertEquals(1500000.0, transaction.getTotalPrice());

    }
}