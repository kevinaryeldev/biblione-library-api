package com.biblione.library_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Biblione Library API")
                        .description("""
                                API principal do sistema de gerenciamento de biblioteca escolar Biblione.

                                Gerencia o acervo de livros, exemplares, empréstimos, reservas e multas.
                                Todos os endpoints protegidos requerem autenticação via JWT Bearer Token.

                                **Perfis de acesso:**
                                - `ADMIN` — bibliotecário com acesso total
                                - `READER` — leitor com acesso restrito às próprias operações
                                """)
                        .version("0.1.0")
                        .contact(new Contact()
                                .name("Biblione")
                                .email("suporte@biblione.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8082")
                                .description("Desenvolvimento local")))
                .tags(List.of(
                        new Tag().name("Livros").description("Gerenciamento do acervo de livros"),
                        new Tag().name("Exemplares").description("Gerenciamento de exemplares físicos"),
                        new Tag().name("Leitores").description("Gerenciamento de leitores cadastrados"),
                        new Tag().name("Empréstimos").description("Controle de empréstimos e devoluções"),
                        new Tag().name("Reservas").description("Gerenciamento da fila de reservas"),
                        new Tag().name("Multas").description("Controle de multas por atraso"),
                        new Tag().name("Política de Empréstimo").description("Configuração das regras de empréstimo")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Token JWT obtido no endpoint de autenticação (auth-service)")));
    }
}
