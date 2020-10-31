package br.com.zup.pact.consumer.integration.product.service

import br.com.zup.pact.consumer.dto.ProductDTO
import br.com.zup.pact.consumer.enums.PaymentMethod
import br.com.zup.pact.provider.EmptyRequest
import br.com.zup.pact.provider.ProductResponse
import br.com.zup.pact.provider.ProductServiceGrpc
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductIntegrationImpl(@Inject val grpcService: GrpcServiceIntegration) : ProductIntegration {
    override fun getAll(): List<ProductDTO> {
        val emptyRequest: EmptyRequest = EmptyRequest.newBuilder().build()
        val stub: ProductServiceGrpc.ProductServiceBlockingStub = grpcService.getProductsResourceBlockingStub()

        val productResponse: Iterator<ProductResponse> = stub.getAll(emptyRequest)
        val products = mutableListOf<ProductDTO>()

        productResponse.forEach {
            products.add(
                    ProductDTO(
                            productId = it.productId,
                            name = it.name,
                            price = it.price,
                            quantity = it.quantity,
                            paymentMethod = PaymentMethod.valueOf(it.paymentMethod)
                    )
            )
        }
        return products
    }
}
