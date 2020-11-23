package br.com.zup.pact.product.provider.dto

import br.com.zup.pact.product.provider.enums.PaymentMethod

data class ProductDTO(
        val productId: Int,
        val name: String,
        val price: Double,
        val quantity: Int,
        val paymentMethod: PaymentMethod
)