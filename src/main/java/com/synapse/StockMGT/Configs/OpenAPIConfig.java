package com.synapse.StockMGT.Configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Stock MGT")
                .version("1.0").description("Stock MGT API Documentation")
                        .contact(new Contact().name("Pawan Hettiarachchi").email("pawan@synapse.lk")));
    }
}
