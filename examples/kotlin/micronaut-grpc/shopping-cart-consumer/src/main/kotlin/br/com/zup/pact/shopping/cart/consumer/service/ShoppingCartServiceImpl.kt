package br.com.zup.pact.shopping.cart.consumer.service

import br.com.zup.pact.shopping.cart.consumer.dto.ProductDTO
import br.com.zup.pact.shopping.cart.consumer.integration.product.service.ProductIntegration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingCartServiceImpl(@Inject private val productIntegration: ProductIntegration) : ShoppingCartService {
    override fun getAllProducts(): List<ProductDTO> {
        return productIntegration.getAll()
    }
}
