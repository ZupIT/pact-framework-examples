package br.com.vinirib.pact.consumer.client.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Modifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;


@Configuration
public class ClientBeans {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .registerTypeAdapter(Json.class, SpringfoxJsontoGsonAdapter.builder().build())
                .serializeNulls()
                .create();
    }

    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConverter() {
        final GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson());
        return gsonHttpMessageConverter;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(gsonHttpMessageConverter());
        return restTemplate;
    }

}
