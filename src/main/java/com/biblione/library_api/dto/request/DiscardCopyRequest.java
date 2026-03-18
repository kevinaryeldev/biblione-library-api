package com.biblione.library_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para descarte de um exemplar do acervo")
public record DiscardCopyRequest(

        @Schema(
                description = "Motivo do descarte do exemplar",
                example = "Exemplar danificado por umidade — páginas ilegíveis",
                maxLength = 500,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Motivo do descarte é obrigatório")
        @Size(max = 500, message = "Motivo deve ter no máximo 500 caracteres")
        String reason

) {}
