package com.biblione.library_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Schema(description = "Dados para cadastro ou atualização de um livro")
public record BookRequest(

        @Schema(
                description = "ISBN-13 do livro (13 dígitos numéricos, sem hífens)",
                example = "9788535914849",
                nullable = true,
                maxLength = 13,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Pattern(regexp = "^[0-9]{13}$", message = "ISBN-13 deve conter exatamente 13 dígitos numéricos")
        @Size(max = 13, message = "ISBN-13 deve ter no máximo 13 caracteres")
        String isbn13,

        @Schema(
                description = "Título do livro",
                example = "1984",
                maxLength = 300,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 300, message = "Título deve ter no máximo 300 caracteres")
        String title,

        @Schema(
                description = "Subtítulo do livro",
                example = "Uma distopia clássica",
                nullable = true,
                maxLength = 300,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 300, message = "Subtítulo deve ter no máximo 300 caracteres")
        String subtitle,

        @Schema(
                description = "Nome da editora",
                example = "Companhia das Letras",
                nullable = true,
                maxLength = 200,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 200, message = "Editora deve ter no máximo 200 caracteres")
        String publisher,

        @Schema(
                description = "Edição do livro",
                example = "1ª edição",
                nullable = true,
                maxLength = 40,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 40, message = "Edição deve ter no máximo 40 caracteres")
        String edition,

        @Schema(
                description = "Ano de publicação (entre 1000 e 2100)",
                example = "1949",
                minimum = "1000",
                maximum = "2100",
                nullable = true,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Min(value = 1000, message = "Ano de publicação deve ser maior que 1000")
        @Max(value = 2100, message = "Ano de publicação deve ser menor que 2100")
        Short publishYear,

        @Schema(
                description = "Código de idioma BCP-47 (ex: pt, en, pt-BR)",
                example = "pt",
                nullable = true,
                maxLength = 10,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Pattern(
                regexp = "^[a-zA-Z]{2,8}(-[a-zA-Z0-9]{2,8})*$",
                message = "Idioma deve seguir o padrão BCP-47 (ex: pt, en, pt-BR)"
        )
        @Size(max = 10, message = "Código de idioma deve ter no máximo 10 caracteres")
        String language,

        @Schema(
                description = "Número de páginas do livro",
                example = "328",
                minimum = "1",
                maximum = "9999",
                nullable = true,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Min(value = 1, message = "Número de páginas deve ser pelo menos 1")
        @Max(value = 9999, message = "Número de páginas deve ser no máximo 9999")
        Short pages,

        @Schema(
                description = "URL da imagem da capa do livro",
                example = "https://example.com/covers/1984.jpg",
                nullable = true,
                maxLength = 500,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 500, message = "URL da capa deve ter no máximo 500 caracteres")
        String coverUrl,

        @Schema(
                description = "Lista de autores do livro (pelo menos um)",
                example = "[\"George Orwell\"]",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotEmpty(message = "É necessário informar pelo menos um autor")
        Set<@Size(max = 200, message = "Nome do autor deve ter no máximo 200 caracteres") String> authors,

        @Schema(
                description = "Lista de categorias do livro (pelo menos uma)",
                example = "[\"Ficção Científica\", \"Distopia\"]",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotEmpty(message = "É necessário informar pelo menos uma categoria")
        Set<@Size(max = 80, message = "Nome da categoria deve ter no máximo 80 caracteres") String> categories

) {}
