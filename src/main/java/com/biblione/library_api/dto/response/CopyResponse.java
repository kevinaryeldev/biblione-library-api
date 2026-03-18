package com.biblione.library_api.dto.response;

import com.biblione.library_api.enums.CopyStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Dados de um exemplar físico do acervo")
public record CopyResponse(

        @Schema(description = "Identificador único do exemplar", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(description = "ID do livro ao qual o exemplar pertence", example = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
        UUID bookId,

        @Schema(description = "Título do livro", example = "1984")
        String bookTitle,

        @Schema(description = "Código de barras do exemplar", example = "978853591484901")
        String barcode,

        @Schema(description = "Status atual do exemplar", example = "AVAILABLE",
                allowableValues = {"AVAILABLE", "LOANED", "RESERVED", "DISCARDED"})
        CopyStatus status,

        @Schema(description = "Data de aquisição do exemplar", example = "2024-03-15", nullable = true)
        LocalDate acquisitionDate,

        @Schema(description = "Data de descarte do exemplar", example = "2025-01-10", nullable = true)
        LocalDate discardDate,

        @Schema(description = "Motivo do descarte", example = "Páginas danificadas por umidade", nullable = true)
        String discardReason,

        @Schema(description = "Data e hora de cadastro do exemplar", example = "2024-03-15T10:30:00Z")
        OffsetDateTime createdAt

) {}
