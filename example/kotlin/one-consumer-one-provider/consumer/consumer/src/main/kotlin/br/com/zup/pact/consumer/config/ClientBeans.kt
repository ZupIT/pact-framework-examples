package br.com.zup.pact.consumer.config

import org.springframework.boot.json.GsonJsonParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ClientBeans {
//    @Bean
//    fun gson(): GsonJsonParser {
//        return GsonJsonParser()
//    }

    @Bean
    fun getRestTemplate(): RestTemplate {
        return RestTemplate()
    }
}