package com.example.crud_application.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonUpdateDto {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    private String middlename;
}
