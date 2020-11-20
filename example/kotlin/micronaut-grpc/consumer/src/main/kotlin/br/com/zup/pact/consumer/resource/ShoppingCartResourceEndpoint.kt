package br.com.zup.pact.consumer.resource

import br.com.zup.pact.consumer.service.ShoppingCartService
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingCartResourceEndpoint (@Inject val shoppingCartService: ShoppingCartService) :
    CartServiceGrpc.CartServiceImplBase() {

    override fun getProducts(request: EmptyRequest?, responseObserver: StreamObserver<CartResponse>?) {
        shoppingCartService.getAllProducts().forEach {
            val response = CartResponse.newBuilder()
                    .setName(it.name)
                    .setPrice(it.price)
                    .setQuantity(it.quantity)
                    .build()

            responseObserver?.onNext(response)
        }
        responseObserver?.onCompleted()
    }
}