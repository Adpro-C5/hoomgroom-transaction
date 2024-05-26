package id.ac.ui.cs.advprog.transaction.model;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name="transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @Column(name = "transaction_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "product_amount", nullable = false)
    private Integer productAmount;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "payment_date", nullable = false)
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date paymentDate;

    @Column(name = "total_price")
    private Double totalPrice;

    public Transaction(){}

    public Transaction(
            Integer userId,
            UUID productId,
            Integer productAmount,
            String promoCode) {

        this.userId = userId;
        this.productId = productId;
        this.productAmount = productAmount;
        this.promoCode = promoCode;
    }
}