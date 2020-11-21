package br.com.zup.pact.shopping.cart.consumer.dto

import br.com.zup.pact.shopping.cart.consumer.enums.PaymentMethod

data class ProductDTO(
        val productId: Int,
        val name: String,
        val price: Double,
        val quantity: Int,
        val paymentMethod: PaymentMethod
)
