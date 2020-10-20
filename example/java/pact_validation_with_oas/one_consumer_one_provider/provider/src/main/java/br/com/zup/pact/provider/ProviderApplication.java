package br.com.zup.pact.provider;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    @Bean
    public OpenAPI openAPIConfig(){

        return new OpenAPI()
                .info(new Info()
                        .title("Account application Documentation (Open API Specification)")
                        .version("1.0")
                        .description("Account API Open API ")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0")
                                .url("http://www.zup.com.br")));
    }
}
