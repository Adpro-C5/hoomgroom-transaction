package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.dto.ProductDTO;
import id.ac.ui.cs.advprog.transaction.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TotalPriceHandler extends Handler{

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void handle(Transaction transaction, String jwtToken){

        String getProductUrl = "http://34.87.141.138/product/" + transaction.getProductId();
        ResponseEntity<ProductDTO> response = restTemplate.getForEntity(getProductUrl, ProductDTO.class);

        ProductDTO product = response.getBody();

        if(product == null){
            throw new IllegalArgumentException("Product with id " + transaction.getProductId() + " not found");
        }

        Double productPrice = product.getPrice();

        if(product.getDiscountedPrice() != null){
            productPrice = product.getDiscountedPrice();
        }

        Double totalPrice = productPrice * transaction.getProductAmount();
        transaction.setTotalPrice(totalPrice);

        handleNext(transaction, jwtToken);
    }
}

