package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.auth.AuthHelper;
import id.ac.ui.cs.advprog.transaction.dto.BalanceDecreaseDTO;
import id.ac.ui.cs.advprog.transaction.dto.ProfileDTO;
import id.ac.ui.cs.advprog.transaction.model.Transaction;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class BalanceCheckHandler extends Handler{

    private AuthHelper authHelper = new AuthHelper();
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void handle(Transaction transaction, String jwtToken){

        ProfileDTO userProfile = authHelper.getUserProfile(jwtToken);
        if (userProfile == null) {
            throw new IllegalArgumentException("Unauthorized request");
        }

        System.out.println(userProfile);

        Long balance = userProfile.getBalance();

        if(balance < transaction.getTotalPrice()){
            throw new IllegalArgumentException("Your balance is not enough");
        }

        // decrease user's balance
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwtToken);

        BalanceDecreaseDTO balanceDecreaseDTO = new BalanceDecreaseDTO(userProfile.getId(), -((Math.round(transaction.getTotalPrice()))));

        HttpEntity<BalanceDecreaseDTO> entity = new HttpEntity<>(balanceDecreaseDTO, headers);

        ResponseEntity<ProfileDTO> response = restTemplate.exchange(
                "http://34.143.253.15/profile/balance/reduce",
                HttpMethod.PUT,
                entity,
                ProfileDTO.class
        );

        if(response.getStatusCode().value() == 400){
            throw new IllegalArgumentException(response.getBody().getMessage());
        }

        handleNext(transaction, jwtToken);
    }
}

