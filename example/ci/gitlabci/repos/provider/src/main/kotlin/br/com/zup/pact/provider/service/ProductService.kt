package br.com.zup.pact.provider.service

import br.com.zup.pact.provider.dto.ProductDTO

interface ProductService {
    fun getAll(): List<ProductDTO>
}
