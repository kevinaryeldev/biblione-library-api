package com.biblione.library_api.controller;

import com.biblione.library_api.dto.request.LoanPolicyRequest;
import com.biblione.library_api.dto.response.LoanPolicyResponse;
import com.biblione.library_api.exception.ErrorResponse;
import com.biblione.library_api.mapper.LoanPolicyMapper;
import com.biblione.library_api.service.LoanPolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Política de Empréstimo", description = "Configuração das regras que governam empréstimos, renovações, reservas e multas")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/loan-policy")
@RequiredArgsConstructor
public class LoanPolicyController {

    private final LoanPolicyService loanPolicyService;
    private final LoanPolicyMapper loanPolicyMapper;

    @Operation(summary = "Consultar política ativa", description = "Retorna a política de empréstimos atualmente ativa. Acessível por ADMIN e READER.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Política retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Perfil sem permissão para esta operação",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'READER')")
    public LoanPolicyResponse getActive() {
        return loanPolicyMapper.toResponse(loanPolicyService.findActive());
    }

    @Operation(summary = "Atualizar política de empréstimo", description = "Substitui a política de empréstimos ativa pelas novas configurações informadas. Requer perfil ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Política atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Perfil sem permissão para esta operação",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public LoanPolicyResponse update(@Valid @RequestBody LoanPolicyRequest request) {
        return loanPolicyMapper.toResponse(
                loanPolicyService.update(loanPolicyMapper.toEntity(request))
        );
    }
}
