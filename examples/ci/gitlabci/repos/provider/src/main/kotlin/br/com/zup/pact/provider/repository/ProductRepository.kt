package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.dto.ProductDTO

interface ProductRepository {
    fun getAllRepository(): List<ProductDTO>
}
