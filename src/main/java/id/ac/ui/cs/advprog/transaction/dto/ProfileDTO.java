package id.ac.ui.cs.advprog.transaction.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ProfileDTO{
    private String message;
    private Integer id;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String username;
    private String email;
    private String address;
    private Long balance;

    public ProfileDTO(String message, Integer id, String fullName, LocalDate birthDate, String gender, String username, String email, String address, Long balance) {
        this.message = message;
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.username = username;
        this.email = email;
        this.address = address;
        this.balance = balance;
    }
}
