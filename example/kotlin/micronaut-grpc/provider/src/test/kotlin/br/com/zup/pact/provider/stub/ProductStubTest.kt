package br.com.zup.pact.provider.stub

import br.com.zup.pact.provider.entity.ProductEntity
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
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

        Assertions.assertEquals(numberOfStubs, result.size)
    }
}