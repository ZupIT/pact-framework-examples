package br.com.zup.pact.product.provider.service

import br.com.zup.pact.product.provider.dto.ProductDTO

interface ProductService {
    fun getAll(): List<ProductDTO>
}
