package br.com.zup.pact.shopping.cart.consumer.service

import br.com.zup.pact.shopping.cart.consumer.dto.ProductDTO
import br.com.zup.pact.shopping.cart.consumer.enums.PaymentMethod
import br.com.zup.pact.shopping.cart.consumer.integration.product.service.ProductIntegration
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ShoppingCartServiceImplTest {

    private val productIntegrationMock: ProductIntegration = mockk()
    private val shoppingCartService: ShoppingCartService = ShoppingCartServiceImpl(
            productIntegrationMock
    )

    @BeforeEach
    fun setUp() {
        clearMocks(productIntegrationMock)
    }

    @Test
    fun `should getAll returns a list of ProductDTO`() {

        every { productIntegrationMock.getAll() }
                .returns(
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

        val actualReturn: List<ProductDTO> = shoppingCartService.getAllProducts()

        assertThat(actualReturn).asList()
        assertThat(actualReturn).isEqualTo(
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