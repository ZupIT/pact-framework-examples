package br.com.zup.pact.product.provider.repository

import br.com.zup.pact.product.provider.dto.ProductDTO
import br.com.zup.pact.product.provider.enums.PaymentMethod
import br.com.zup.pact.product.provider.stub.ProductStub
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ProductRepositoryImplTest {

    private val productStubMock: ProductStub = mockk()
    private val productRepository: ProductRepository = ProductRepositoryImpl(productStubMock)

    @BeforeEach
    fun setUp() {
        clearMocks(productStubMock)
    }

    @Test
    fun `should productRepository returns a getAllProductsDTOFormat from the productStub`() {

        every { productStubMock.getAllProductsDTOFormat() }
                .returns(listOf(
                        ProductDTO(1, "any product", 150.0, 12,
                                PaymentMethod.DEFERRED_PAYMENT
                        ),
                        ProductDTO(2, "another product", 134.0, 6,
                                PaymentMethod.CASH_PAYMENT
                        )
                ))

        val expectProductsDTO = productRepository.getAllRepository()

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