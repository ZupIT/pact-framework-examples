package br.com.zup.pact.provider.entity

import br.com.zup.pact.provider.dto.ProductDTO
import br.com.zup.pact.provider.enums.PaymentMethod

data class ProductEntity (
        val productId: Int,
        val name: String,
        val price: Double,
        val quantity: Int,
        val paymentMethod: PaymentMethod
) {

    fun toProductDTO(): ProductDTO {
        return ProductDTO(
                productId, name, price, quantity, paymentMethod
        )
    }
}
