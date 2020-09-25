package br.com.zup.pact.provider.resource

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.service.AccountService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GRpcService
class AccountResourceImpl(@Autowired val accountService: AccountService)
    : AccountResourceGrpc.AccountResourceImplBase() {

    override fun getAccountDetailsByClientId(request: AccountIdRequest, responseObserver: StreamObserver<AccountDetailsResponse>) {
        val accountDetailsOptional: Optional<AccountDetailsDTO> = accountService.getAccountDetailsByClientId(request.clientId)

        if (accountDetailsOptional.isPresent) {
            val accountDetails = accountDetailsOptional.get()

            val response = AccountDetailsResponse.newBuilder()
                    .setAccountId(accountDetails.accountId)
                    .setBalance(accountDetails.balance)
                    .setAccountType(accountDetails.accountType.toString())
                    .build()

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        }
    }

    override fun getAll(request: EmptyRequest, responseObserver: StreamObserver<AccountDetailsResponse>) {

        accountService.getAll().forEach {

            val response = AccountDetailsResponse.newBuilder()
                    .setAccountId(it.accountId)
                    .setBalance(it.balance)
                    .setAccountType(it.accountType.toString())
                    .build()

            responseObserver.onNext(response)
        };

        responseObserver.onCompleted()
    }

    override fun getBalanceByClientId(request: AccountIdRequest, responseObserver: StreamObserver<BalanceResponse>) {
        val balanceOptional: Optional<BalanceDTO> = accountService.getBalanceByClientId(request.clientId)

        if (balanceOptional.isPresent) {
            val balance = balanceOptional.get()

            val response = BalanceResponse.newBuilder()
                    .setClientId(balance.clientId)
                    .setAccountId(balance.accountId)
                    .setBalance(balance.balance)
                    .build()

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        }
    }

}