package com.biblione.library_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Dados para registro de um novo empréstimo")
public record LoanRequest(

        @Schema(
                description = "ID do livro a ser emprestado. O sistema selecionará automaticamente um exemplar disponível.",
                example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "ID do livro é obrigatório")
        UUID bookId,

        @Schema(
                description = "ID do leitor que receberá o empréstimo",
                example = "7c9e6679-7425-40de-944b-e07fc1f90ae7",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "ID do leitor é obrigatório")
        UUID readerId

) {}
