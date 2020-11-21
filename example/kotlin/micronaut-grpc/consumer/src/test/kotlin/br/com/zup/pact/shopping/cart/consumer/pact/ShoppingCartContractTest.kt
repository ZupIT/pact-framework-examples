package br.com.zup.pact.shopping.cart.consumer.pact

import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import br.com.zup.pact.product.provider.resource.EmptyRequest
import br.com.zup.pact.product.provider.resource.ProductServiceGrpc
import org.apache.commons.collections4.MapUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "grpc-product-provider", port = "1234")
class ShoppingCartContractTest {
    private val headers = MapUtils.putAll(HashMap<String, String>(), arrayOf(
            "Content-Type", "application/json"
    ))

    @Pact(provider = "grpc-product-provider", consumer = "grpc-product-consumer")
    fun getAllProductsPact(builder: PactDslWithProvider): RequestResponsePact {
        val bodyResponse = PactDslJsonBody()
                .integerType("productId")
                .stringType("name")
                .numberType("price")
                .integerType("quantity")
                .stringType("paymentMethod")

        return builder
                .given("get all products")
                .uponReceiving("br.com.zup.pact.product.provider.resource.ProductService/getAll")
                .path("/grpc/br.com.zup.pact.product.provider.resource.ProductService/getAll")
                .method("POST")
                .body("{}")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(bodyResponse)
                .toPact()
    }

    @Test
    @PactTestFor(pactMethod = "getAllProductsPact", providerName = "grpc-product-provider")
    fun getAllProductsTest() {
        val emptyRequest: EmptyRequest = EmptyRequest.newBuilder().build()
        val productStub = ProductServiceGrpc
                .newBlockingStub(GrpcHttpChannelKotlin("localhost", 1234))
        val response = productStub.getAll(emptyRequest)
        assertThat(response).isNotNull

        response.forEach {
            assertThat(it.name).isNotNull
            assertThat(it.price).isNotNull
            assertThat(it.quantity).isNotNull
            assertThat(it.paymentMethod).isNotNull

        }
    }
}