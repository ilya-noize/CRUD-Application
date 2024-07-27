package com.example.crud_application.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.crud_application.entity.PersonLimit.MAX_EMAIL_LENGTH;
import static com.example.crud_application.entity.PersonLimit.MAX_FIRSTNAME_LENGTH;
import static com.example.crud_application.entity.PersonLimit.MAX_LASTNAME_LENGTH;
import static com.example.crud_application.entity.PersonLimit.MAX_MIDDLENAME_LENGTH;
import static com.example.crud_application.entity.PersonLimit.MIN_EMAIL_LENGTH;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create new person")
public class PersonNewDto {
    @Schema(description = "E-mail", example = "mail@yahoo.com")
    @Size(min = MIN_EMAIL_LENGTH, max = MAX_EMAIL_LENGTH)
    @Email
    private String email;

    @Schema(description = "FirstName", example = "Mike")
    @Size(max = MAX_FIRSTNAME_LENGTH)

    @NotBlank
    private String firstname;

    @Schema(description = "Surname", example = "Rogers")
    @Size(max = MAX_LASTNAME_LENGTH)
    @NotBlank
    private String lastname;

    @Schema(description = "Middle Name", example = "Alexandrovich")
    @Size(max = MAX_MIDDLENAME_LENGTH)
    private String middlename;
}
