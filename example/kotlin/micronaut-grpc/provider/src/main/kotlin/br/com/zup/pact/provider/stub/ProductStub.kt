package br.com.zup.pact.provider.stub

import br.com.zup.pact.provider.dto.ProductDTO
import br.com.zup.pact.provider.entity.ProductEntity
import br.com.zup.pact.provider.enums.PaymentMethod
import kotlin.random.Random

class ProductStub {

    private val numberOfStubs = 10
    private val name = "Any Product"
    private val price = 10.0
    private val initialQuantity = 1

    private val products = mutableMapOf<Int, ProductEntity>()

    private fun randomPayment(): PaymentMethod {
        val paymentMethod = PaymentMethod.values()
        return paymentMethod[Random.nextInt(0, paymentMethod.size)]
    }

    fun createStubs() {
        products.putAll(
                (1..numberOfStubs)
                        .map { ProductEntity(it, name, price, it.plus(initialQuantity), randomPayment()) }
                        .associateBy({ it.productId }, { it })
        )
    }

    fun getAllProductsDTOFormat(): List<ProductDTO> {
        return products.values.map(ProductEntity::toProductDTO)
    }
}
