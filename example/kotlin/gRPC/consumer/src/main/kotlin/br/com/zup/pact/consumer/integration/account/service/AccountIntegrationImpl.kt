package br.com.zup.pact.consumer.integration.account.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.provider.resource.AccountIdRequest
import br.com.zup.pact.provider.resource.AccountResourceGrpc
import br.com.zup.pact.provider.resource.AccountResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountIntegrationImpl(@Autowired private val grpcService: GrpcService) : AccountIntegrationService {

    override fun getBalance(accountId: Int): BalanceDTO? {

        val balanceRequest = AccountIdRequest.newBuilder()
                .setAccountId(accountId)
                .build()

        val stub: AccountResourceGrpc.AccountResourceBlockingStub = grpcService.getAccountResourceBlockingStub()

        val balanceResponse: AccountResponse = stub.findById(balanceRequest)

        return BalanceDTO(
                accountId = balanceResponse.accountId,
                balance = balanceResponse.balance
        )

    }

}