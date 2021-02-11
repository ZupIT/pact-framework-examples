package br.com.vinirib.provider.pact.account.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Modifier;

@Configuration
public class AccountBeans {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .registerTypeAdapter(Json.class, SpringfoxJsontoGsonAdapter.builder().build())
                .serializeNulls()
                .create();
    }


}
