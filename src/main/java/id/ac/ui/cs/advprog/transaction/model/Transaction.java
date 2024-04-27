package id.ac.ui.cs.advprog.transaction.model;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Transaction {
    private String userUsername;
    private UUID id;
    private UUID productId;
    private Integer productAmount;
    private String promoCode;
    private Date paymentDate;
    private Double totalPrice;

    public Transaction(String userUsername){
        this.userUsername = userUsername;
        this.id = UUID.randomUUID();
        this.productId = null;
        this.productAmount = null;
        this.promoCode = null;
        this.paymentDate = null;
        this.totalPrice = null;
    }

    public Transaction(String userUsername,
                       UUID productId,
                       Integer productAmount,
                       String promoCode,
                       Date paymentDate,
                       Double totalPrice){
        this.userUsername = userUsername;
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.productAmount = productAmount;
        this.promoCode = promoCode;
        this.paymentDate = paymentDate;
        this.totalPrice = totalPrice;
    }

}