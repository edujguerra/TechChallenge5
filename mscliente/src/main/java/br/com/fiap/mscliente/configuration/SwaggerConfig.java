package br.com.fiap.mscliente.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public List<GroupedOpenApi> apis() {
        return Arrays.asList(
                GroupedOpenApi.builder()
                        .group("cliente-api")
                        .pathsToMatch("/clientes/**")
                        .build()
        );
    }
}