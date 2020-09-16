package br.com.zup.pact.consumer.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
class ApiConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(appInfo());
    }

    private fun appInfo(): ApiInfo {
        return ApiInfo(
                "Client API",
                "Client detail from Account of an bank API Made by Vinicius Ribeiro",
                "API TOS",
                "Terms of service",
                Contact("Vinicius Ribeiro", "http://zup.com.br",
                        "vinicius.aparecido@zup.com.br"),
                "License of API", "API licence URL", Collections.emptyList())
    }
}