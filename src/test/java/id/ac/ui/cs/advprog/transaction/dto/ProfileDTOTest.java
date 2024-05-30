package id.ac.ui.cs.advprog.transaction.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProfileDTOTest {

    ProfileDTO profileDTO;
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    DateTimeFormatter newDateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");

    @BeforeEach
    void setUp() {

        profileDTO = new ProfileDTO("dummy-message", 1, "Aldi Taher", LocalDate.parse("10 08 2002", newDateFormatter), "M", "alditaher123", "abcde@gmail.com", "Jl. In Ajah", (long)500000);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMessage() {
        assertEquals("dummy-message", this.profileDTO.getMessage());
    }

    @Test
    void testGetId() {
        assertEquals(1, this.profileDTO.getId());
    }

    @Test
    void testGetFullName() {
        assertEquals("Aldi Taher", this.profileDTO.getFullName());
    }

    @Test
    void testGetBirthDate() {
        assertEquals(LocalDate.parse("10 08 2002", newDateFormatter), this.profileDTO.getBirthDate());
    }

    @Test
    void testGetGender() {
        assertEquals("M", this.profileDTO.getGender());
    }

    @Test
    void testGetUsername() {
        assertEquals("alditaher123", this.profileDTO.getUsername());
    }

    @Test
    void testGetEmail() {
        assertEquals("abcde@gmail.com", this.profileDTO.getEmail());
    }

    @Test
    void testGetAddress() {
        assertEquals("Jl. In Ajah", this.profileDTO.getAddress());
    }

    @Test
    void testGetBalance() {
        assertEquals((long)500000, this.profileDTO.getBalance());
    }
}