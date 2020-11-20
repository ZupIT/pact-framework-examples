package br.com.zup.pact.consumer.resource

import br.com.zup.pact.consumer.dto.ProductDTO
import br.com.zup.pact.consumer.enums.PaymentMethod
import br.com.zup.pact.consumer.service.ShoppingCartService
import io.grpc.stub.StreamObserver
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ShoppingCartResourceEndpointTest {
    private val shoppingCartServiceMock: ShoppingCartService = mockk()
    private val shoppingCartResourceEndpoint: ShoppingCartResourceEndpoint = ShoppingCartResourceEndpoint(
            shoppingCartServiceMock
    )

    @BeforeEach
    fun setUp() {
        clearMocks(shoppingCartServiceMock)
    }

    @Test
    fun `should getAll returns all Products from the service`() {

        every { shoppingCartServiceMock.getAllProducts() }
                .returns (
                        listOf (
                                ProductDTO (
                                        1,
                                        "Any product",
                                        100.0,
                                        3,
                                        PaymentMethod.CASH_PAYMENT
                                )
                        )
                )
        val responseObserver: StreamObserver<CartResponse> = mockk()
        every { responseObserver.onNext(any()) } just Runs
        every { responseObserver.onCompleted() } just Runs

        shoppingCartResourceEndpoint.getProducts(
                EmptyRequest.newBuilder().build(),
                responseObserver
        )

        verify(exactly = 1) {
            responseObserver.onNext(
                    CartResponse.newBuilder()
                            .setName("Any product")
                            .setPrice(100.0)
                            .setQuantity(3)
                            .build()
            )
        }

        verify(exactly = 1) {
            responseObserver.onCompleted()
        }
    }

}