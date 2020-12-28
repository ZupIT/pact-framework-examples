package br.com.zup.pact.provider.dto

import br.com.zup.pact.provider.enums.PaymentMethod

data class ProductDTO(
        val productId: Int,
        val name: String,
        val price: Double,
        val quantity: Int,
        val paymentMethod: PaymentMethod
)