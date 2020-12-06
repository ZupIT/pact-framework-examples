package br.com.zup.pact.provider.service

import br.com.zup.pact.provider.dto.ProductDTO
import br.com.zup.pact.provider.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductServiceImpl(@Inject val productRepository: ProductRepository) : ProductService {
    override fun getAll(): List<ProductDTO> {
        return productRepository.getAllRepository()
    }
}
