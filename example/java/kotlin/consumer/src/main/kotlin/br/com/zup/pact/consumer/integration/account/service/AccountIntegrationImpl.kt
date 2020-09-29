package br.com.zup.pact.consumer.integration.account.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.provider.resource.AccountIdRequest
import br.com.zup.pact.provider.resource.AccountResourceGrpc
import br.com.zup.pact.provider.resource.AccountResponse
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PreDestroy


@Service
class AccountIntegrationImpl(
        @Value("\${integration.account.balance.address}")
        private val accountBalanceAddress: String,
        @Value("\${integration.account.balance.port}")
        private val accountBalancePort: Int
) : AccountIntegrationService {

    val channel: ManagedChannel = ManagedChannelBuilder.forAddress(accountBalanceAddress, accountBalancePort)
            .usePlaintext()
            .build()

    override fun getBalance(accountId: Int): Optional<BalanceDTO> {

        val balanceRequest = AccountIdRequest.newBuilder()
                .setAccountId(accountId)
                .build()

        val stub: AccountResourceGrpc.AccountResourceBlockingStub = AccountResourceGrpc.newBlockingStub(channel)
        val balanceResponse: AccountResponse = stub.findById(balanceRequest)

        return Optional.of(
                BalanceDTO(
                        accountId = balanceResponse.accountId,
                        balance = balanceResponse.balance
                )
        )
    }

    @PreDestroy
    private fun destroy() {
        channel.shutdown()
    }

}