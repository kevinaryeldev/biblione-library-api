package com.biblione.library_api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(description = "Resposta de erro padronizada (estilo Open Finance Brasil)")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(

        @Schema(description = "Lista de erros ocorridos na requisição")
        List<ErrorDetail> errors,

        @Schema(description = "Metadados da requisição")
        Meta meta

) {
    @Schema(description = "Detalhe de um erro individual")
    public record ErrorDetail(
            @Schema(description = "Código de identificação do erro", example = "NOT_FOUND")
            String code,
            @Schema(description = "Título legível do erro", example = "Not Found")
            String title,
            @Schema(description = "Descrição detalhada do erro", example = "Livro não encontrado: 3fa85f64-5717-4562-b3fc-2c963f66afa6")
            String detail
    ) {}

    @Schema(description = "Metadados da requisição que gerou o erro")
    public record Meta(
            @Schema(description = "Data e hora da requisição", example = "2024-03-15T10:30:00Z")
            OffsetDateTime requestDateTime
    ) {}

    public static ErrorResponse of(String code, String title, String detail) {
        return new ErrorResponse(
                List.of(new ErrorDetail(code, title, detail)),
                new Meta(OffsetDateTime.now())
        );
    }

    public static ErrorResponse ofValidation(List<ErrorDetail> details) {
        return new ErrorResponse(
                details,
                new Meta(OffsetDateTime.now())
        );
    }
}
