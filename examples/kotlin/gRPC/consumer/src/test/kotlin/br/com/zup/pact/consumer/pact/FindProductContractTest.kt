package br.com.zup.pact.consumer.pact

import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import br.com.zup.pact.provider.resource.AccountIdRequest
import br.com.zup.pact.provider.resource.AccountResourceGrpc
import org.apache.commons.collections4.MapUtils
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "grpc-account-provider", port = "1234")
class FindProductContractTest {
    private val headers = MapUtils.putAll(HashMap<String, String>(), arrayOf(
            "Content-Type", "application/json"
    ))

    @Pact(provider = "grpc-account-provider", consumer = "grpc-account-consumer")
    fun createGetBalancePact(builder: PactDslWithProvider): RequestResponsePact {
        val bodyResponse = PactDslJsonBody()
                .integerType("accountId")
        return builder
                .given("get balance of accountId 1")
                .uponReceiving("br.com.zup.pact.provider.resource.AccountResource/findById")
                .path("/grpc/br.com.zup.pact.provider.resource.AccountResource/findById")
                .method("POST")
                .body("{\"accountId\": 1}") // "1" as string
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(bodyResponse)
                .toPact()
    }

    @Test
    @PactTestFor(pactMethod = "createGetBalancePact", providerName = "grpc-account-provider")
    fun findUserRoleChangesByAppAndDateTest() {
        val balanceRequest = AccountIdRequest.newBuilder()
                .setAccountId(1)
                .build()
        val accountStub = AccountResourceGrpc
                .newBlockingStub(GrpcHttpChannel("localhost", 1234))
        val response = accountStub.findById(balanceRequest)
        MatcherAssert.assertThat(response, Matchers.notNullValue())
        MatcherAssert.assertThat(response.accountId, Matchers.notNullValue())
        MatcherAssert.assertThat(response.balance, Matchers.notNullValue())
    }
}