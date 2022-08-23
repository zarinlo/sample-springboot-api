package sample.api.stocks.configs;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI documentation() {
        return new OpenAPI()
                .info(new Info().title("Stocks API")
                        .description("Sample Java Spring Boot API using MongoDB Atlas")
                        .version("2.0.1")
                        .contact(new Contact().name("Zarin Lokhandwala").url("https://zarin.io/")))
                .externalDocs(new ExternalDocumentation()
                        .description("Stocks API Documentation")
                        .url("https://github.com/zarinlo/sample-springboot-api#readme"));
    }
}