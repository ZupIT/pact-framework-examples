package br.com.zup.pact.shopping.cart.consumer.service

import br.com.zup.pact.shopping.cart.consumer.dto.ProductDTO

interface ShoppingCartService {
    fun getAllProducts(): List<ProductDTO>
}