package com.example.crud_application.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.crud_application.entity.PersonLimit.MAX_FIRSTNAME_LENGTH;
import static com.example.crud_application.entity.PersonLimit.MAX_LASTNAME_LENGTH;
import static com.example.crud_application.entity.PersonLimit.MAX_MIDDLENAME_LENGTH;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Update person")
public class PersonUpdateDto {
    @Schema(description = "ID", example = "1")
    @Positive
    @NotNull
    private Long id;

    @Schema(description = "Name", example = "Mike")
    @Size(max = MAX_FIRSTNAME_LENGTH)
    private String firstname;

    @Schema(description = "Surname", example = "Rogers")
    @Size(max = MAX_LASTNAME_LENGTH)
    private String lastname;

    @Schema(description = "Middle Name", example = "Alexandrovich")
    @Size(max = MAX_MIDDLENAME_LENGTH)
    private String middlename;
}
