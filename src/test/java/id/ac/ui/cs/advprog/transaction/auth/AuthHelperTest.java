package id.ac.ui.cs.advprog.transaction.auth;

import id.ac.ui.cs.advprog.transaction.dto.ProfileDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AuthHelperTest {

    @InjectMocks
    AuthHelper authHelper;

    @Mock
    RestTemplate restTemplate;

    ProfileDTO profileDTO;

    @BeforeEach
    void setUp() {
        profileDTO = new ProfileDTO("dummy-message", 1, null, null, null, null, null, null, null);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserProfile() {

        ResponseEntity<ProfileDTO> responseEntity = new ResponseEntity<>(profileDTO, HttpStatus.OK);

        doReturn(responseEntity).when(restTemplate).exchange(
                eq("http://34.143.253.15/profile"),
                eq(HttpMethod.GET),
                ArgumentMatchers.<HttpEntity<?>>any(),
                eq(ProfileDTO.class)
        );

        ProfileDTO result = authHelper.getUserProfile("jwtToken");

        assertEquals("dummy-message", result.getMessage());
    }

    @Test
    void testGetRole() {

        ResponseEntity<String> responseEntity = new ResponseEntity<>("BUYER", HttpStatus.OK);

        doReturn(responseEntity).when(restTemplate).exchange(
                eq("http://34.143.253.15/profile/role"),
                eq(HttpMethod.GET),
                ArgumentMatchers.<HttpEntity<?>>any(),
                eq(String.class)
        );

        String result = authHelper.getUserRole("jwtToken");

        assertEquals("BUYER", result);
    }
}