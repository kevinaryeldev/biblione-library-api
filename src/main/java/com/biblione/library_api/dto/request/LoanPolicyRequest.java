package com.biblione.library_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Dados para atualização da política de empréstimos")
public record LoanPolicyRequest(

        @Schema(
                description = "Nome descritivo da política",
                example = "Política Padrão 2024",
                maxLength = 80,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Nome da política é obrigatório")
        @Size(max = 80, message = "Nome deve ter no máximo 80 caracteres")
        String name,

        @Schema(
                description = "Prazo do empréstimo em dias corridos",
                example = "14",
                minimum = "1",
                maximum = "365",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Prazo de empréstimo é obrigatório")
        @Min(value = 1, message = "Prazo de empréstimo deve ser de pelo menos 1 dia")
        @Max(value = 365, message = "Prazo de empréstimo deve ser de no máximo 365 dias")
        Short loanDays,

        @Schema(
                description = "Número máximo de renovações permitidas por empréstimo",
                example = "2",
                minimum = "0",
                maximum = "10",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Máximo de renovações é obrigatório")
        @Min(value = 0, message = "Máximo de renovações deve ser zero ou positivo")
        @Max(value = 10, message = "Máximo de renovações deve ser no máximo 10")
        Short maxRenewals,

        @Schema(
                description = "Número máximo de empréstimos simultâneos por leitor",
                example = "3",
                minimum = "1",
                maximum = "20",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Máximo de empréstimos simultâneos é obrigatório")
        @Min(value = 1, message = "Máximo de empréstimos simultâneos deve ser pelo menos 1")
        @Max(value = 20, message = "Máximo de empréstimos simultâneos deve ser no máximo 20")
        Short maxSimultaneous,

        @Schema(
                description = "Valor da multa por dia de atraso (máximo R$ 99,99)",
                example = "1.50",
                minimum = "0.00",
                maximum = "99.99",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Valor da multa por dia é obrigatório")
        @DecimalMin(value = "0.00", message = "Multa por dia não pode ser negativa")
        @DecimalMax(value = "99.99", message = "Multa por dia deve ser no máximo R$ 99,99")
        @Digits(integer = 2, fraction = 2, message = "Multa por dia deve ter no máximo 2 dígitos inteiros e 2 decimais")
        BigDecimal finePerDay,

        @Schema(
                description = "Número de dias que o leitor tem para retirar um exemplar reservado",
                example = "3",
                minimum = "1",
                maximum = "30",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Prazo de retirada da reserva é obrigatório")
        @Min(value = 1, message = "Prazo de retirada deve ser de pelo menos 1 dia")
        @Max(value = 30, message = "Prazo de retirada deve ser de no máximo 30 dias")
        Short reservationHoldDays

) {}
