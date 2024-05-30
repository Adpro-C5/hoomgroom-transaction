package id.ac.ui.cs.advprog.transaction.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ShipmentDTOTest {

    ShipmentDTO shipmentDTO;
    String id;
    String orderId;
    String status;

    @BeforeEach
    void setUp() {
        shipmentDTO = new ShipmentDTO();
        shipmentDTO.setId("1");
        shipmentDTO.setOrderId("7af4edd5-97b8-49cc-8b75-4d4a9d0029ef");
        shipmentDTO.setStatus("DIKIRIM");
    }

    @Test
    void testGetId() {
        assertEquals("1", this.shipmentDTO.getId());
    }

    @Test
    void testGetOrderId() {
        assertEquals("7af4edd5-97b8-49cc-8b75-4d4a9d0029ef", this.shipmentDTO.getOrderId());
    }

    @Test
    void testGetStatus() {
        assertEquals("DIKIRIM", this.shipmentDTO.getStatus());
    }


}