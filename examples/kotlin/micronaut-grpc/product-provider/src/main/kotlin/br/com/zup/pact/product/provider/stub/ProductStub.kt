package br.com.zup.pact.product.provider.stub

import br.com.zup.pact.product.provider.dto.ProductDTO
import br.com.zup.pact.product.provider.entity.ProductEntity
import br.com.zup.pact.product.provider.enums.PaymentMethod
import javax.annotation.PostConstruct
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class ProductStub {

    private val numberOfStubs = 10
    private val name = "Any Product"
    private val price = 10.0
    private val initialQuantity = 1
    val products = mutableMapOf<Int, ProductEntity>()

    private fun randomPayment(): PaymentMethod {
        val paymentMethod = PaymentMethod.values()
        return paymentMethod[Random.nextInt(0, PaymentMethod.values().size)]
    }

    @PostConstruct
    fun createStubs() {
        products.putAll(
                (1..numberOfStubs)
                        .map { ProductEntity(it, name, it * price, it.plus(initialQuantity), randomPayment()) }
                        .associateBy({ it.productId }, { it })
        )
    }

    fun getAllProductsDTOFormat(): List<ProductDTO> {
        return products.values.map(ProductEntity::toProductDTO)
    }
}
