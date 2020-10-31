package br.com.zup.pact.consumer.service

import br.com.zup.pact.consumer.dto.ProductDTO

interface ShoppingCartService {
    fun getAllProducts(): List<ProductDTO>
}