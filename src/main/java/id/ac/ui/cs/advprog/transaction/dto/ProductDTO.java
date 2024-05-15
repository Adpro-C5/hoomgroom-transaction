package id.ac.ui.cs.advprog.transaction.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class ProductDTO{
    UUID id;
    String productName;
    // private ArrayList<String> categories;
    String description;
    String imagePath;
    Double price;
    Double discountedPrice;
    Integer sales;
}