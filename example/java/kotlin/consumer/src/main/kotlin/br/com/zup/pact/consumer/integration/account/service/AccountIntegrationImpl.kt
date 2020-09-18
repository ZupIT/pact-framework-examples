package br.com.zup.pact.consumer.integration.account.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountIntegrationImpl() : AccountIntegrationService {

    override fun getBalance(accountId: Int): Optional<BalanceDTO> {
        TODO("Not yet implemented")
    }


}