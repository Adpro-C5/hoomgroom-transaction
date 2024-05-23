package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.dto.ProductDTO;
import id.ac.ui.cs.advprog.transaction.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TotalPriceHandler extends Handler{

    private final RestTemplate restTemplate;

    public TotalPriceHandler(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public void handle(Transaction transaction){

        // Sementara menggunakan localhost
        String getProductUrl = "http://localhost:8002/product/" + transaction.getProductId();
        ResponseEntity<ProductDTO> response = restTemplate.getForEntity(getProductUrl, ProductDTO.class);

        ProductDTO product = response.getBody();

        if(product == null){
            throw new IllegalArgumentException("Product with id " + transaction.getProductId() + " not found");
        }

        Double productPrice = product.getPrice();

        Double totalPrice = productPrice * transaction.getProductAmount();
        transaction.setTotalPrice(totalPrice);

        handleNext(transaction);
    }
}

