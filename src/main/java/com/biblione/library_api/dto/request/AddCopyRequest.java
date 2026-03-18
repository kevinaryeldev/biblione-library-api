package com.biblione.library_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Dados para adição de um novo exemplar ao acervo")
public record AddCopyRequest(

        @Schema(
                description = "Código de barras único do exemplar físico",
                example = "978853591484901",
                maxLength = 40,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Código de barras é obrigatório")
        @Size(max = 40, message = "Código de barras deve ter no máximo 40 caracteres")
        String barcode,

        @Schema(
                description = "Data de aquisição do exemplar (não pode ser data futura)",
                example = "2024-03-15",
                nullable = true,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @PastOrPresent(message = "Data de aquisição não pode ser uma data futura")
        LocalDate acquisitionDate

) {}
