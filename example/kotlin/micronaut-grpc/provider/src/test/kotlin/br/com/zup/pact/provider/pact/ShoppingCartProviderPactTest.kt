package br.com.zup.pact.provider.pact

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.VerificationReports
import au.com.dius.pact.provider.junit.loader.PactBroker
import au.com.dius.pact.provider.junit.loader.PactFolder
import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import br.com.zup.pact.provider.dto.ProductDTO
import br.com.zup.pact.provider.enums.PaymentMethod
import br.com.zup.pact.provider.service.ProductService
import br.com.zup.pact.provider.service.ProductServiceImpl
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.Mockito.mock
import java.net.URL
import javax.inject.Inject


@Provider("grpc-product-provider")
@PactFolder("pacts")
@PactBroker(host = "localhost", port = "9292")
@MicronautTest(rebuildContext = true)
@VerificationReports(value = ["console","json"])
class ShoppingCartProviderPactTest {

    @Inject
    lateinit var productService: ProductService

    @Inject
    lateinit var server: EmbeddedServer

    @MockBean(ProductServiceImpl::class)
    fun dependecy(): ProductService {
        return mock(ProductService::class.java)
    }

    @BeforeEach
    fun setUp(context: PactVerificationContext) {
        //context.target = HttpTestTarget(server.host, server.port)
        context.target = HttpTestTarget.fromUrl(URL("http://localhost:${server.port}"))
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun pactVerificationTestTemplate(context: PactVerificationContext) {
        context.verifyInteraction()
    }

    @State("get all products")
    fun getAllProducts() {
        val products: List<ProductDTO> = listOf(
                ProductDTO(
                        1,
                        "Any product",
                        50.0,
                        5,
                        PaymentMethod.DEFERRED_PAYMENT
                )
        )
       BDDMockito.given(productService.getAll()).willReturn(products)
//        every { productService.getAll() }
//                .returns(products)
    }
}