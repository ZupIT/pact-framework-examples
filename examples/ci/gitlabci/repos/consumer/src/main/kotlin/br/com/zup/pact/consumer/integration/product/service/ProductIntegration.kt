package br.com.zup.pact.consumer.integration.product.service

import br.com.zup.pact.consumer.dto.ProductDTO

interface ProductIntegration {
    fun getAll(): List<ProductDTO>
}
