package com.example.crud_application.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonNewDto {
    @Email
    @UniqueElements
    private String email;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    private String middlename;
}
