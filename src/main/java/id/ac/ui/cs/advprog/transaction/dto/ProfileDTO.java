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
}
