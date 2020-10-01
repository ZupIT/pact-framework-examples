package br.com.zup.pact.consumer.integration.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate


class AccountIntegrationServiceImpl: AccountIntegrationService {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Value("integration.account.balance.url")
    private lateinit var accountBalanceUrl: String

    override fun getBalance(accountId: Int): BalanceDTO? {
        val responseEntity: ResponseEntity<BalanceDTO> = restTemplate.getForEntity(accountBalanceUrl, BalanceDTO::class.java, accountId)
        return responseEntity.body
    }
}