package br.com.zup.pact.consumer.integration.product.service

import br.com.zup.pact.provider.ProductServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class GrpcServiceIntegration {

    private val channel: ManagedChannel = ManagedChannelBuilder
            .forAddress("http://localhost", 50051)
            .usePlaintext()
            .build()

    fun getProductsResourceBlockingStub(): ProductServiceGrpc.ProductServiceBlockingStub {
        return ProductServiceGrpc.newBlockingStub(channel)
    }
}