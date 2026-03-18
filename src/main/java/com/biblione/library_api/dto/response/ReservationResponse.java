package com.biblione.library_api.dto.response;

import com.biblione.library_api.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Dados de uma reserva")
public record ReservationResponse(

        @Schema(description = "Identificador único da reserva", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(description = "ID do livro reservado", example = "1e7f8a43-2c1d-4f5e-8b6a-9d0c2e3f4a5b")
        UUID bookId,

        @Schema(description = "Título do livro reservado", example = "1984")
        String bookTitle,

        @Schema(description = "ID do leitor que fez a reserva", example = "7c9e6679-7425-40de-944b-e07fc1f90ae7")
        UUID readerId,

        @Schema(description = "Nome do leitor", example = "João da Silva")
        String readerName,

        @Schema(description = "Status atual da reserva", example = "WAITING",
                allowableValues = {"WAITING", "READY", "EXPIRED", "CANCELLED"})
        ReservationStatus status,

        @Schema(description = "Posição do leitor na fila de espera (1 = próximo a ser atendido)", example = "2", nullable = true)
        Short queuePosition,

        @Schema(description = "Data e hora em que o exemplar ficou disponível para retirada (status READY)", example = "2024-03-20T08:00:00Z", nullable = true)
        OffsetDateTime readyAt,

        @Schema(description = "Data e hora em que a reserva expira (prazo para retirada)", example = "2024-03-23T23:59:59Z", nullable = true)
        OffsetDateTime expiresAt,

        @Schema(description = "Data e hora de criação da reserva", example = "2024-03-15T10:30:00Z")
        OffsetDateTime createdAt

) {}
