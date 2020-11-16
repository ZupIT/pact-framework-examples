package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.dto.ProductDTO
import br.com.zup.pact.provider.stub.ProductStub
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl(@Inject val productStub: ProductStub): ProductRepository {
    override fun getAllRepository(): List<ProductDTO> {
        return productStub.getAllProductsDTOFormat()
    }
}
