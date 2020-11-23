package br.com.zup.pact.product.provider.service

import br.com.zup.pact.product.provider.dto.ProductDTO
import br.com.zup.pact.product.provider.enums.PaymentMethod
import br.com.zup.pact.product.provider.repository.ProductRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ProductServiceImplTest {

    private val productRepositoryMock: ProductRepository = mockk()
    private val productService: ProductService = ProductServiceImpl(productRepositoryMock)

    @BeforeEach
    fun setUp() {
        clearMocks(productRepositoryMock)
    }

    @Test
    fun `should productService returns a List with all products`() {

        every { productRepositoryMock.getAllRepository() }
                .returns(listOf(
                        ProductDTO(1, "any product", 150.0, 12,
                                PaymentMethod.DEFERRED_PAYMENT
                        ),
                        ProductDTO(2, "another product", 134.0, 6,
                                PaymentMethod.CASH_PAYMENT
                        )
                ))

        val expectProductsDTO = productService.getAll()

        assertThat(expectProductsDTO).isEqualTo(
                listOf(
                        ProductDTO(1, "any product", 150.0, 12,
                                PaymentMethod.DEFERRED_PAYMENT
                        ),
                        ProductDTO(2, "another product", 134.0, 6,
                                PaymentMethod.CASH_PAYMENT
                        )
                )
        )
    }
}