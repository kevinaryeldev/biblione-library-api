package com.biblione.library_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para bloqueio de um leitor")
public record BlockReaderRequest(

        @Schema(
                description = "Motivo do bloqueio do leitor",
                example = "Leitor com multas em aberto há mais de 30 dias",
                maxLength = 500,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Motivo do bloqueio é obrigatório")
        @Size(max = 500, message = "Motivo deve ter no máximo 500 caracteres")
        String reason

) {}
