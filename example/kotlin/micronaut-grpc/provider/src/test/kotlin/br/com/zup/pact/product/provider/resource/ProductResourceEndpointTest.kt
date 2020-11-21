package br.com.zup.pact.product.provider.resource

import br.com.zup.pact.product.provider.dto.ProductDTO
import br.com.zup.pact.product.provider.enums.PaymentMethod
import br.com.zup.pact.product.provider.service.ProductService
import io.grpc.stub.StreamObserver
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ProductResourceEndpointTest {

    private val productServiceMock: ProductService = mockk()
    private val productResourceEndpoint: ProductResourceEndpoint = ProductResourceEndpoint(productServiceMock)

    @Test
    fun `should getAll returns all products from the service`() {

        every { productServiceMock.getAll() }
                .returns(listOf(
                        ProductDTO(1, "any product", 150.0, 12,
                                PaymentMethod.DEFERRED_PAYMENT
                        ),
                        ProductDTO(2, "another product", 134.0, 6,
                                PaymentMethod.CASH_PAYMENT
                        )
                ))

        val responseObserver: StreamObserver<ProductResponse> = mockk()
        every { responseObserver.onNext(any()) } just Runs
        every { responseObserver.onCompleted() } just Runs

        productResourceEndpoint.getAll(
                EmptyRequest.newBuilder().build(),
                responseObserver
        )

        verify(exactly = 1) {
            responseObserver.onNext(
                    ProductResponse.newBuilder()
                            .setProductId(1)
                            .setName("any product")
                            .setPrice(150.0)
                            .setQuantity(12)
                            .setPaymentMethod(PaymentMethod.DEFERRED_PAYMENT.toString())
                            .build()
            )
        }

        verify(exactly = 1) {
            responseObserver.onCompleted()
        }
    }
}