package com.biblione.library_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Dados de um livro do acervo")
public record BookResponse(

        @Schema(description = "Identificador único do livro", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(description = "ISBN-13 do livro", example = "9788535914849", nullable = true)
        String isbn13,

        @Schema(description = "Título do livro", example = "1984")
        String title,

        @Schema(description = "Subtítulo do livro", example = "Uma distopia clássica", nullable = true)
        String subtitle,

        @Schema(description = "Nome da editora", example = "Companhia das Letras", nullable = true)
        String publisher,

        @Schema(description = "Edição do livro", example = "1ª edição", nullable = true)
        String edition,

        @Schema(description = "Ano de publicação", example = "1949", nullable = true)
        Short publishYear,

        @Schema(description = "Código de idioma BCP-47", example = "pt", nullable = true)
        String language,

        @Schema(description = "Número de páginas", example = "328", nullable = true)
        Short pages,

        @Schema(description = "URL da imagem da capa", example = "https://example.com/covers/1984.jpg", nullable = true)
        String coverUrl,

        @Schema(description = "Lista de autores do livro", example = "[\"George Orwell\"]")
        Set<String> authors,

        @Schema(description = "Lista de categorias do livro", example = "[\"Ficção Científica\", \"Distopia\"]")
        Set<String> categories,

        @Schema(description = "Data e hora de cadastro do livro", example = "2024-03-15T10:30:00Z")
        OffsetDateTime createdAt

) {}
