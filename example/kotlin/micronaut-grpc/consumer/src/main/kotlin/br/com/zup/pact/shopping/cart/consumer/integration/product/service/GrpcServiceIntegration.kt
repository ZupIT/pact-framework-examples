package br.com.zup.pact.shopping.cart.consumer.integration.product.service

import br.com.zup.pact.product.provider.resource.ProductServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.micronaut.context.annotation.Value
import javax.annotation.PreDestroy
import javax.inject.Singleton

@Singleton
class GrpcServiceIntegration(@Value("\${integration.provider.port}")
                             private val providerPort: Int,
                             @Value("\${integration.provider.host}")
                             private val providerHost: String
) {

    private val channel: ManagedChannel = ManagedChannelBuilder
            .forAddress(providerHost, providerPort)
            .usePlaintext()
            .build()

    fun getProductsResourceBlockingStub(): ProductServiceGrpc.ProductServiceBlockingStub {
        return ProductServiceGrpc.newBlockingStub(channel)
    }

    @PreDestroy
    private fun destroy() {
        channel.shutdown()
    }
}