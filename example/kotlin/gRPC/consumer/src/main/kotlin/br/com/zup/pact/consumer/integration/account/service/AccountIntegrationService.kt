package br.com.zup.pact.consumer.integration.account.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import java.util.*

interface AccountIntegrationService {
    fun getBalance(accountId: Int): BalanceDTO?
}