package br.com.zup.pact.product.provider.repository

import br.com.zup.pact.product.provider.dto.ProductDTO

interface ProductRepository {
    fun getAllRepository(): List<ProductDTO>
}
