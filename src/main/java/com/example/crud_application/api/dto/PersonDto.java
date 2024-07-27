package com.example.crud_application.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response")
public class PersonDto {
    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "Name", example = "Mike")
    private String firstname;

    @Schema(description = "Surname", example = "Rogers")
    private String lastname;

    @Schema(description = "Middle Name", example = "Alexandrovich")
    private String middlename;
}
