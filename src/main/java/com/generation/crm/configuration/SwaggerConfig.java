package com.generation.crm.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI springBlogPessoalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Projeto Integrador - Sistema CRM Médico")
                        .description("""
                               <html><b> Projeto Integrador - Sistema CRM Médico\n\n 
                                Vitor Cavalcante - TESTER\n
                                Priscila Bortniuk - PRODUCT OWNER\n
                                Victória Lara - DESENVOLVEDORA\n
                                Guilherme Kaynam - DESENVOLVEDOR\n
                                Rubio Dainton - DESENVOLVEDOR\n
                                Kemilly Fagundes - DESENVOLVEDORA\n                         
                                Bruna Bosco - DESENVOLVEDORA\n
                                Bianca Jesus - DESENVOLVEDORA </b></html>""")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Generation Brasil")
                                .url("https://brazil.generation.org/"))
                        .contact(new Contact()
                                .name("Grupo04")
                                .url("https://github.com/Projeto-Integrador-grupo-4-Modelo/projeto_crm")
                                .email("integradorgen4@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github")
                        .url("https://github.com/Projeto-Integrador-grupo-4-Modelo/projeto_crm"));

    }

    @Bean
    OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
                    .forEach(operation -> {
                        ApiResponses apiResponses = operation.getResponses();
                        apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
                        apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
                        apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
                        apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
                        apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
                        apiResponses.addApiResponse("403", createApiResponse("Acesso Proibido!"));
                        apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
                        apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));
                    }));
        };
    }
    private ApiResponse createApiResponse(String message){
        return new ApiResponse().description(message);
    }
}
