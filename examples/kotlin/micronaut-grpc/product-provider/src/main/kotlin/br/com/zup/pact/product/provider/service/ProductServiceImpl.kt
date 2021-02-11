package br.com.zup.pact.product.provider.service

import br.com.zup.pact.product.provider.dto.ProductDTO
import br.com.zup.pact.product.provider.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductServiceImpl(@Inject val productRepository: ProductRepository) : ProductService {
    override fun getAll(): List<ProductDTO> {
        return productRepository.getAllRepository()
    }
}
