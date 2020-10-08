package br.com.zup.pact.consumer.resource

import br.com.zup.pact.client.resource.BalanceResponse
import br.com.zup.pact.client.resource.ClientRequest
import br.com.zup.pact.client.resource.ClientResourceGrpc
import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.service.ClientService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired

@GRpcService
class ClientResourceImpl(@Autowired val clientService: ClientService)
    :ClientResourceGrpc.ClientResourceImplBase() {

    override fun getBalance(request: ClientRequest, responseObserver: StreamObserver<BalanceResponse>) {
        super.getBalance(request, responseObserver)

        val balance: BalanceDTO? = clientService.getBalance(request.clientId)

        if (balance == null) {
            responseObserver.onError(balance)
        } else {
            val response = BalanceResponse.newBuilder()
                    .setAccountId(balance.accountId)
                    .setBalance(balance.balance)
                    .build()

            responseObserver.onNext(response)
        }

        responseObserver.onCompleted()
    }
}