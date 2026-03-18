package com.biblione.library_api.dto.response;

import com.biblione.library_api.enums.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Dados de um empréstimo")
public record LoanResponse(

        @Schema(description = "Identificador único do empréstimo", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(description = "ID do exemplar físico emprestado", example = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
        UUID copyId,

        @Schema(description = "ID do livro emprestado", example = "1e7f8a43-2c1d-4f5e-8b6a-9d0c2e3f4a5b")
        UUID bookId,

        @Schema(description = "Título do livro emprestado", example = "1984")
        String bookTitle,

        @Schema(description = "ID do leitor que realizou o empréstimo", example = "7c9e6679-7425-40de-944b-e07fc1f90ae7")
        UUID readerId,

        @Schema(description = "Nome do leitor", example = "João da Silva")
        String readerName,

        @Schema(description = "Data e hora em que o empréstimo foi realizado", example = "2024-03-15T10:30:00Z")
        OffsetDateTime loanedAt,

        @Schema(description = "Data de vencimento do empréstimo", example = "2024-03-29")
        LocalDate dueDate,

        @Schema(description = "Data e hora da devolução (nulo se ainda ativo)", example = "2024-03-28T14:00:00Z", nullable = true)
        OffsetDateTime returnedAt,

        @Schema(description = "Data e hora da última renovação (nulo se não renovado)", example = "2024-03-22T09:00:00Z", nullable = true)
        OffsetDateTime renewedAt,

        @Schema(description = "Status atual do empréstimo", example = "ACTIVE",
                allowableValues = {"ACTIVE", "RETURNED", "OVERDUE", "RENEWED"})
        LoanStatus status,

        @Schema(description = "ID do funcionário que registrou o empréstimo", example = "5a3f2b1c-4d6e-7f8a-9b0c-1d2e3f4a5b6c")
        UUID registeredBy

) {}
