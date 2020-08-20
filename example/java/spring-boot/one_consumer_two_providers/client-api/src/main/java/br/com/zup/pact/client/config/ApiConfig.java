package br.com.zup.pact.client.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@Profile("dev")
public class ApiConfig {

    /**
     * To add more features visit
     * https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Client API",
                "Client detail from Account of an bank API Made by Vinicius Ribeiro.",
                "API TOS",
                "Terms of service",
                new Contact("Vinícius Ribeiro", "http://zup.com.br", "vinicius.aparecido@zup.com.br"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
