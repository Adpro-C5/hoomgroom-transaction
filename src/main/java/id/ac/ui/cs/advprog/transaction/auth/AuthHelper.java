package id.ac.ui.cs.advprog.transaction.auth;

import id.ac.ui.cs.advprog.transaction.dto.ProfileDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthHelper {

    private RestTemplate restTemplate = new RestTemplate();

    public ProfileDTO getUserProfile(String jwtToken){
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", jwtToken);

            HttpEntity<String> entity = new HttpEntity<>(null, headers);

            ResponseEntity<ProfileDTO> response = restTemplate.exchange(
                    "http://34.143.253.15/profile",
                    HttpMethod.GET,
                    entity,
                    ProfileDTO.class
            );

            return response.getBody();

        } catch(Exception e){
            return null;
        }
    }

    public String getUserRole(String jwtToken){
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", jwtToken);

            HttpEntity<String> entity = new HttpEntity<>(null, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    "http://34.143.253.15/profile/role",
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return response.getBody();

        } catch(Exception e){
            return null;
        }
    }
}