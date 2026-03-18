package com.biblione.library_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Dados da política de empréstimos")
public record LoanPolicyResponse(

        @Schema(description = "Identificador único da política", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(description = "Nome descritivo da política", example = "Política Padrão 2024")
        String name,

        @Schema(description = "Prazo do empréstimo em dias corridos", example = "14")
        Short loanDays,

        @Schema(description = "Número máximo de renovações por empréstimo", example = "2")
        Short maxRenewals,

        @Schema(description = "Número máximo de empréstimos simultâneos por leitor", example = "3")
        Short maxSimultaneous,

        @Schema(description = "Valor da multa por dia de atraso em reais", example = "1.50")
        BigDecimal finePerDay,

        @Schema(description = "Prazo em dias para retirada de exemplar reservado", example = "3")
        Short reservationHoldDays,

        @Schema(description = "Indica se esta é a política atualmente ativa", example = "true")
        boolean active,

        @Schema(description = "Data e hora de criação da política", example = "2024-01-01T00:00:00Z")
        OffsetDateTime createdAt

) {}
