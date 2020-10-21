package br.com.zup.pact.provider.resource

import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.service.AccountService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired

@GRpcService
class AccountResourceImpl(@Autowired val accountService: AccountService)
    : AccountResourceGrpc.AccountResourceImplBase() {

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

    override fun findById(request: AccountIdRequest, responseObserver: StreamObserver<AccountResponse>) {
        val balance: BalanceDTO? = accountService.findByAccountId(request.accountId)

        val response = AccountResponse.newBuilder()
                .setAccountId(balance?.accountId!!)
                .setBalance(balance.balance)
                .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

}