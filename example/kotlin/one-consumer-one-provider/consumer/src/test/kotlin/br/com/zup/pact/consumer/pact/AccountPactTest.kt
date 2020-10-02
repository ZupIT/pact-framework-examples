package br.com.zup.pact.consumer.pact

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import org.apache.http.HttpResponse
import org.apache.http.client.fluent.Request
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "AccountBalanceProvider", port = "1234")
class AccountPactTest {

    private val balanceUrlWorking: String = "/v1/accounts/balance/1"
    private val balanceUrlNotWorking: String = "/v1/accounts/balance/1000"
    private val header = mapOf("Content-Type" to "application/json")

    @Pact(provider = "AccountBalanceProvider", consumer = "AccountBalanceConsumer")
    fun balanceEndpointTest(builder: PactDslWithProvider): RequestResponsePact {
        val bodyResponse: PactDslJsonBody = PactDslJsonBody()
                .integerType("accountId")
                .integerType("clientId")
                .numberType("balance")

        return builder.given("get balance of accountId 1")
                .uponReceiving("A request to $balanceUrlWorking")
                .path(balanceUrlWorking)
                .method("GET")
                .willRespondWith()
                .headers(header)
                .status(200)
                .body(bodyResponse)
                .toPact()
    }

    @Pact(provider = "AccountBalanceProvider", consumer = "AccountBalanceConsumer")
    fun balanceEndpointNotWorkingTest(builder: PactDslWithProvider): RequestResponsePact {
        return builder.given("No accounts exist from accountId 1000")
                .uponReceiving("A request to $balanceUrlNotWorking")
                .path(balanceUrlNotWorking)
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact()
    }

    @Test
    @PactTestFor(pactMethod = "balanceEndpointTest", providerName = "AccountBalanceProvider")
    fun testBalanceWorking(mockServer: MockServer) {
        val httpResponse: HttpResponse = Request.Get(mockServer.getUrl() + balanceUrlWorking)
                .execute().returnResponse()
        Assertions.assertThat(httpResponse.statusLine.statusCode).isEqualTo(200)
    }

    @Test
    @PactTestFor(pactMethod = "balanceEndpointNotWorkingTest", providerName = "AccountBalanceProvider")
    fun testBalanceNotWorking(mockServer: MockServer) {
        val httpResponse: HttpResponse = Request.Get(mockServer.getUrl() + balanceUrlNotWorking)
                .execute().returnResponse()
        Assertions.assertThat(httpResponse.statusLine.statusCode).isEqualTo(404)
    }

}