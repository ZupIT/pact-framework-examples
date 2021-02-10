package br.com.zup.pact.shopping.cart.consumer.integration.product.service

import br.com.zup.pact.shopping.cart.consumer.dto.ProductDTO

interface ProductIntegration {
    fun getAll(): List<ProductDTO>
}
