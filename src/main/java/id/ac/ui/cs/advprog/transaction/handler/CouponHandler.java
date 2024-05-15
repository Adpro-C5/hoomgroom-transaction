package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.dto.PromoCodeDTO;
import id.ac.ui.cs.advprog.transaction.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CouponHandler extends Handler{

    ResponseEntity<PromoCodeDTO> response;

    @Override
    public void handle(Transaction transaction){

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        RestTemplate restTemplate = new RestTemplate();

        if(transaction.getPromoCode() == null){
            handleNext(transaction);
            return;
        }

        try{
            String getCoupontUrl = "http://localhost:8002/promo_code/name/" + transaction.getPromoCode();
            response = restTemplate.getForEntity(getCoupontUrl, PromoCodeDTO.class);
        } catch(Exception e){
            throw new IllegalArgumentException("Promo code " + transaction.getPromoCode() + " not found");
        }

        PromoCodeDTO promoCode = response.getBody();

        Double purchase = transaction.getTotalPrice();

        if(today.isAfter(promoCode.getExpiredDate()) || today.isEqual(promoCode.getExpiredDate())){
            throw new IllegalArgumentException("Your coupon code has expired by " + promoCode.getExpiredDate().format(formatter));
        }
        else if(purchase < promoCode.getMinimumPurchase()){
            throw new IllegalArgumentException("Your purchase is " + purchase + " while the minimum purchase for this coupon is " + promoCode.getMinimumPurchase());
        }

        Double newPrice = purchase * ( (100.0 - getNumberAtEnd(promoCode.getName())) / 100.0);
        transaction.setTotalPrice(newPrice);

        handleNext(transaction);
    }


    // credit: chatGPT
    public static Integer getNumberAtEnd(String input) {
        // Use a regular expression to find the numeric part at the end of the string
        Pattern pattern = Pattern.compile("(\\d+)$");
        Matcher matcher = pattern.matcher(input);

        // If a match is found, return the number, otherwise return null
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : null;
    }
}

