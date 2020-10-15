package br.com.zup.pact.consumer.resource

import br.com.zup.pact.client.resource.*
import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.service.ClientService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired

@GRpcService
class ClientResourceImpl(@Autowired val clientService: ClientService)
    : ClientResourceGrpc.ClientResourceImplBase() {

    override fun getAll(request: EmptyRequest, responseObserver: StreamObserver<ClientResponse>) {
        clientService.getAll().forEach {

            val response = ClientResponse.newBuilder()
                    .setId(it.id)
                    .setAccountId(it.accountId)
                    .setName(it.name)
                    .setFinalName(it.finalName)
                    .setAge(it.age)
                    .build()
            responseObserver.onNext(response)
        }
        responseObserver.onCompleted()
    }

    override fun getBalance(request: ClientRequest, responseObserver: StreamObserver<BalanceResponse>) {
        val balance: BalanceDTO? = clientService.getBalance(request.clientId)

        if (balance == null) {
            responseObserver.onError(balance) //TODO tem que testar essa condição
        } else {
            val response = balance?.accountId?.let {
                BalanceResponse.newBuilder()
                        .setClientId(request.clientId)
                        .setAccountId(it)
                        .setBalance(balance.balance)
                        .build()
            }

            responseObserver.onNext(response)
        }

        responseObserver.onCompleted()
    }
}