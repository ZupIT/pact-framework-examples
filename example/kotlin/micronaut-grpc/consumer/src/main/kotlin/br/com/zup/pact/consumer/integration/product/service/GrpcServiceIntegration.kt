package br.com.zup.pact.consumer.integration.product.service

import br.com.zup.pact.provider.ProductServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import javax.inject.Singleton

@Singleton
class GrpcServiceIntegration {

    private val channel: ManagedChannel = ManagedChannelBuilder
            .forAddress("localhost", 6565)
            .usePlaintext()
            .build()

    fun getProductsResourceBlockingStub(): ProductServiceGrpc.ProductServiceBlockingStub {
        return ProductServiceGrpc.newBlockingStub(channel)
    }
}