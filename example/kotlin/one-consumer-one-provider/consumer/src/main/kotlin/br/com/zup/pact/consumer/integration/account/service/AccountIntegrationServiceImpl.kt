package br.com.zup.pact.consumer.integration.account.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AccountIntegrationServiceImpl (
        @Value("\${integration.account.balance.url}") private val accountBalanceUrl: String
): AccountIntegrationService {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder().build()
    }

    @Autowired
    private lateinit var restTemplate: RestTemplate

    override fun getBalance(accountId: Int): BalanceDTO? {
        val responseEntity: ResponseEntity<BalanceDTO> = restTemplate.getForEntity(accountBalanceUrl, BalanceDTO::class.java, accountId)
        return responseEntity.body
    }
}