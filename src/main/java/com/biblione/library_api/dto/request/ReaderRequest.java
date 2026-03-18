package com.biblione.library_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Dados para cadastro ou atualização de um leitor")
public record ReaderRequest(

        @Schema(
                description = "Identificador único do leitor (UUID gerado pelo auth-service)",
                example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "ID do leitor é obrigatório")
        UUID id,

        @Schema(
                description = "Endereço de e-mail do leitor",
                example = "joao.silva@escola.edu.br",
                maxLength = 254,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail deve ter formato válido")
        @Size(max = 254, message = "E-mail deve ter no máximo 254 caracteres")
        String email,

        @Schema(
                description = "Nome completo do leitor",
                example = "João da Silva",
                maxLength = 120,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
        String name,

        @Schema(
                description = "Número de matrícula ou registro do leitor na instituição",
                example = "2024001234",
                nullable = true,
                maxLength = 40,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 40, message = "Número de matrícula deve ter no máximo 40 caracteres")
        String registrationNumber,

        @Schema(
                description = "Telefone de contato do leitor (com ou sem código do país)",
                example = "+55 11 99999-9999",
                nullable = true,
                maxLength = 20,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Pattern(
                regexp = "^\\+?[0-9\\s\\-().]{7,20}$",
                message = "Telefone deve conter entre 7 e 20 dígitos, podendo incluir +, espaços, hífens e parênteses"
        )
        @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
        String phone,

        @Schema(
                description = "Endereço residencial do leitor",
                example = "Rua das Flores, 123, Apto 45, São Paulo - SP, 01310-100",
                nullable = true,
                maxLength = 300,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 300, message = "Endereço deve ter no máximo 300 caracteres")
        String address

) {}
