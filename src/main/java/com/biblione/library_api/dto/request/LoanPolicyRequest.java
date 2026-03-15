package com.biblione.library_api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record LoanPolicyRequest(
        @NotBlank @Size(max = 80) String name,
        @NotNull @Min(1) Short loanDays,
        @NotNull @Min(0) Short maxRenewals,
        @NotNull @Min(1) Short maxSimultaneous,
        @NotNull @DecimalMin("0.00") BigDecimal finePerDay,
        @NotNull @Min(1) Short reservationHoldDays
) {}
