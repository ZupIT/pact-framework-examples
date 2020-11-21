package br.com.zup.pact.shopping.cart.consumer.integration.product.service

import br.com.zup.pact.shopping.cart.consumer.dto.ProductDTO
import br.com.zup.pact.shopping.cart.consumer.enums.PaymentMethod
import br.com.zup.pact.product.provider.resource.EmptyRequest
import br.com.zup.pact.product.provider.resource.ProductResponse
import br.com.zup.pact.product.provider.resource.ProductServiceGrpc
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductIntegrationImpl(@Inject val grpcService: GrpcServiceIntegration) : ProductIntegration {
    override fun getAll(): List<ProductDTO> {
        val emptyRequest: EmptyRequest = EmptyRequest.newBuilder().build()
        val stub: ProductServiceGrpc.ProductServiceBlockingStub = grpcService.getProductsResourceBlockingStub()

        val productResponse: Iterator<ProductResponse> = stub.getAll(emptyRequest)

        return productResponse.asSequence().map {
            ProductDTO(
                    productId = it.productId,
                    name = it.name,
                    price = it.price,
                    quantity = it.quantity,
                    paymentMethod = PaymentMethod.valueOf(it.paymentMethod)
            )
        }.toList()
    }
}
