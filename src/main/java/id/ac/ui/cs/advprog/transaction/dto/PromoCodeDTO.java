package id.ac.ui.cs.advprog.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class PromoCodeDTO{
    private UUID id;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredDate;
    private Double minimumPurchase;
}