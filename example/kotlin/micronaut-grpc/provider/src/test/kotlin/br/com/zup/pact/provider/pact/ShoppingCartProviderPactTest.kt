package br.com.zup.pact.provider.pact

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.loader.PactBroker
import au.com.dius.pact.provider.junit.loader.PactFolder
import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import br.com.zup.pact.provider.Application
import br.com.zup.pact.provider.dto.ProductDTO
import br.com.zup.pact.provider.enums.PaymentMethod
import br.com.zup.pact.provider.service.ProductService
import br.com.zup.pact.provider.service.ProductServiceImpl
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Value
import io.micronaut.context.env.Environment
import io.micronaut.runtime.Micronaut
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.MicronautJunit5Extension
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.annotations.TestOnly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject


@Provider("grpc-product-provider")
@PactFolder("pacts")
@PactBroker(host = "localhost", port = "9292")
@MicronautTest
//@MicronautTest(application = Application::class)
@ExtendWith(MicronautJunit5Extension::class)
//@ConfigurationProperties("micronaut-http")
class ShoppingCartProviderPactTest {
    @Inject
    lateinit var productService: ProductService

    @Inject
    lateinit var server: EmbeddedServer

    @MockBean(ProductServiceImpl::class)
    fun dependecy(): ProductService {
        return mock(ProductService::class.java)
    }

//    fun main(args: Array<String>) {
//        Micronaut.build()
//                .args(*args)
//                .packages("br.com.zup.pact.provider")
//                .start()
//    }


//    @BeforeEach
//    @Throws(MalformedURLException::class)
//    fun setUp(context: PactVerificationContext) {
//        context.target = HttpTestTarget.fromUrl(URL("http://localhost:$localPort"))
//    }

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