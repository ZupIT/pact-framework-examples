package br.com.zup.pact.consumer.resource

import br.com.zup.pact.consumer.service.ShoppingCartService
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingCartResourceEndpoint (@Inject val shoppingCartService: ShoppingCartService) :
    CartServiceGrpc.CartServiceImplBase() {

    override fun getAll(request: EmptyRequest?, responseObserver: StreamObserver<ProductResponse>?) {
        shoppingCartService.getAllProducts().forEach {
            val response = ProductResponse.newBuilder()
                    .setProductId(it.productId)
                    .setName(it.name)
                    .setPrice(it.price)
                    .setQuantity(it.quantity)
                    .setPaymentMethod(it.paymentMethod.toString())
                    .build()

            responseObserver?.onNext(response)
        }
        responseObserver?.onCompleted()
    }
}