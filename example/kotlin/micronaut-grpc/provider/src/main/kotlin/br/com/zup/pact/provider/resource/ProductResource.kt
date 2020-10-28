package br.com.zup.pact.provider.resource

import br.com.zup.pact.provider.*
import br.com.zup.pact.provider.service.ProductService
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductResource (@Inject val productService: ProductService) : ProductServiceGrpc.ProductServiceImplBase() {

    override fun getAll(request: EmptyRequest?, responseObserver: StreamObserver<ProductResponse>?) {
        productService.getAll().forEach {
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
