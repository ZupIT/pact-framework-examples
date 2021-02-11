package br.com.zup.pact.consumer.integration.account.service

import br.com.zup.pact.provider.resource.AccountResourceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PreDestroy

@Service
class GrpcService(
        @Value("\${integration.account.balance.address}")
        private val accountBalanceAddress: String,
        @Value("\${integration.account.balance.port}")
        private val accountBalancePort: Int
) {

    private val channel: ManagedChannel = ManagedChannelBuilder
            .forAddress(accountBalanceAddress, accountBalancePort)
            .usePlaintext()
            .build()

    fun getAccountResourceBlockingStub(): AccountResourceGrpc.AccountResourceBlockingStub {
        return AccountResourceGrpc.newBlockingStub(channel)
    }

    @PreDestroy
    private fun destroy() {
        channel.shutdown()
    }
}