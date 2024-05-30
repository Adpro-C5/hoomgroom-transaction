package id.ac.ui.cs.advprog.transaction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentDTO {
    String id;
    String orderId;
    String status;
}