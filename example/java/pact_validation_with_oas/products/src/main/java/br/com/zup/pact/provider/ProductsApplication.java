package br.com.zup.pact.provider;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}

	@Bean
	public OpenAPI openAPIConfig(
			@Value("${application-description}") String appDescription,
			@Value("${application-version}") String appVersion){

		return new OpenAPI()
				.info(new Info()
						.title("Product application OAS (Open API Specification)")
						.version(appVersion)
						.description(appDescription)
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0")
								.url("http://www.zup.com.br")));
	}
}
