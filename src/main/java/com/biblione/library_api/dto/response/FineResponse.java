package com.biblione.library_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Dados de uma multa por atraso")
public record FineResponse(

        @Schema(description = "Identificador único da multa", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(description = "ID do empréstimo que gerou a multa", example = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
        UUID loanId,

        @Schema(description = "ID do leitor multado", example = "7c9e6679-7425-40de-944b-e07fc1f90ae7")
        UUID readerId,

        @Schema(description = "Nome do leitor multado", example = "João da Silva")
        String readerName,

        @Schema(description = "Número de dias em atraso", example = "5")
        Short daysOverdue,

        @Schema(description = "Valor total da multa em reais", example = "7.50")
        BigDecimal amount,

        @Schema(description = "Data e hora do pagamento (nulo se ainda pendente)", example = "2024-04-02T14:00:00Z", nullable = true)
        OffsetDateTime paidAt,

        @Schema(description = "Data e hora da dispensa da multa (nulo se não dispensada)", example = "2024-04-01T09:00:00Z", nullable = true)
        OffsetDateTime waivedAt,

        @Schema(description = "Data e hora de criação da multa", example = "2024-04-01T01:00:00Z")
        OffsetDateTime createdAt

) {}
