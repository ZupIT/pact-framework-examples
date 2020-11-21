package br.com.zup.pact.product.provider.stub

import br.com.zup.pact.product.provider.dto.ProductDTO
import br.com.zup.pact.product.provider.entity.ProductEntity
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class ProductStubTest {

    @Inject
    private lateinit var productStub: ProductStub

    @Test
    fun `should productStub returns 10 ProductEntity`() {
        val numberOfStubs = 10
        val result: Map<Int, ProductEntity> = productStub.products

        assertThat(numberOfStubs).isEqualTo(result.size)
    }

    @Test
    fun `should getAllProductsDTOFormat returns List of ProductDTO`() {
        val result: List<ProductDTO> = productStub.getAllProductsDTOFormat()

        assertThat(result).asList()

        result.forEach {
            assertThat(it).isInstanceOf(ProductDTO::class.java)
        }
    }
}