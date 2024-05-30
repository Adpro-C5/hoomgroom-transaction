package id.ac.ui.cs.advprog.transaction.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class ProductDTO{
    UUID id;
    String productName;
    ArrayList<String> categories;
    String description;
    String imagePath;
    Double price;
    Double discountedPrice;
    Integer sales;
}