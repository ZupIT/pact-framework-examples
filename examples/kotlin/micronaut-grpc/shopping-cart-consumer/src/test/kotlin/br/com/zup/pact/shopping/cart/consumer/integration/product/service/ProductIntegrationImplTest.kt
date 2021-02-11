package br.com.zup.pact.shopping.cart.consumer.integration.product.service

import br.com.zup.pact.shopping.cart.consumer.dto.ProductDTO
import br.com.zup.pact.shopping.cart.consumer.enums.PaymentMethod
import br.com.zup.pact.product.provider.resource.*
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ProductIntegrationImplTest {

    private val grpcServiceMock: GrpcServiceIntegration = mockk()
    private val productIntegrationImpl: ProductIntegration = ProductIntegrationImpl(grpcServiceMock)

    @BeforeEach
    fun setUp() {
        clearMocks(grpcServiceMock)
    }

    @Test
    fun `should getAll products returns a list ProductDTO`() {

        val stubMock: ProductServiceGrpc.ProductServiceBlockingStub = mockk()

        every { stubMock.getAll(any()) }
                .returns(
                        mutableListOf<ProductResponse>(
                                ProductResponse.newBuilder()
                                        .setProductId(1)
                                        .setName("Any product")
                                        .setPrice(100.0)
                                        .setQuantity(3)
                                        .setPaymentMethod(PaymentMethod.CASH_PAYMENT.toString())
                                        .build()
                        ).listIterator()
                )

        every { grpcServiceMock.getProductsResourceBlockingStub() }
                .returns(stubMock)

        val actualReturn = productIntegrationImpl.getAll()

        Assertions.assertThat(actualReturn).isEqualTo(
                listOf(
                        ProductDTO(
                                1,
                                "Any product",
                                100.0,
                                3,
                                PaymentMethod.CASH_PAYMENT
                        )
                )
        )
    }
}