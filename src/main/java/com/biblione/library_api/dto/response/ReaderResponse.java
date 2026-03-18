package com.biblione.library_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Dados de um leitor cadastrado")
public record ReaderResponse(

        @Schema(description = "Identificador único do leitor", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(description = "Endereço de e-mail do leitor", example = "joao.silva@escola.edu.br")
        String email,

        @Schema(description = "Nome completo do leitor", example = "João da Silva")
        String name,

        @Schema(description = "Número de matrícula ou registro na instituição", example = "2024001234", nullable = true)
        String registrationNumber,

        @Schema(description = "Telefone de contato", example = "+55 11 99999-9999", nullable = true)
        String phone,

        @Schema(description = "Endereço residencial", example = "Rua das Flores, 123, São Paulo - SP", nullable = true)
        String address,

        @Schema(description = "Indica se o leitor está bloqueado para novos empréstimos", example = "false")
        boolean blocked,

        @Schema(description = "Motivo do bloqueio (presente apenas se bloqueado)", example = "Multas em aberto", nullable = true)
        String blockedReason,

        @Schema(description = "Data e hora de cadastro do leitor", example = "2024-03-15T10:30:00Z")
        OffsetDateTime createdAt

) {}
