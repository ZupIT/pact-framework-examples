package br.com.zup.pact.provider

import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
class ProviderService : ProviderServiceGrpc.ProviderServiceImplBase() {
    override fun send(request: ProviderRequest?, responseObserver: StreamObserver<ProviderReply>?) {
        val message: String = request?.name ?: "No params found"

        val reply: ProviderReply = ProviderReply.newBuilder().setMessage(message).build()
        responseObserver?.onNext(reply)
        responseObserver?.onCompleted()
    }
}
