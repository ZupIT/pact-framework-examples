package br.com.zup.pact.consumer.integration.account.service.impl

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.integration.account.service.AccountIntegrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AccountIntegrationServiceImpl (
        @Value("\${integration.account.balance.url}") private val accountBalanceUrl: String,
        @Autowired private val restTemplate: RestTemplate
): AccountIntegrationService {

    override fun getBalance(accountId: Int): BalanceDTO? {
        val responseEntity: ResponseEntity<BalanceDTO> = restTemplate.getForEntity(
                accountBalanceUrl,
                BalanceDTO::class.java,
                accountId
        )
        return responseEntity.body
    }
}