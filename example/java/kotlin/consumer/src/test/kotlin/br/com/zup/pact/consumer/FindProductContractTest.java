package br.com.zup.pact.consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import br.com.zup.pact.provider.resource.AccountIdRequest;
import br.com.zup.pact.provider.resource.AccountResourceGrpc;
import br.com.zup.pact.provider.resource.AccountResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "grpc-account-provider", port = "1234")
public class FindProductContractTest {

    private final Map<String, String> headers = MapUtils.putAll(new HashMap<>(), new String[] {
            "Content-Type", "application/json"
    });

    @Pact(provider = "grpc-account-provider", consumer = "grpc-account-consumer")
    public RequestResponsePact createGetBalancePact(PactDslWithProvider builder) throws IOException {

        PactDslJsonBody bodyResponse = new PactDslJsonBody()
                .integerType("accountId");

        return builder
                .given("default state")
                .uponReceiving("br.com.zup.pact.provider.resource.AccountResource/findById")
                .path("/grpc/br.com.zup.pact.provider.resource.AccountResource/findById")
                .method("POST")
                .body("{\n\"accountId\": 1\n}") // "1" as string
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(bodyResponse)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createGetBalancePact", providerName = "grpc-account-provider")
    public void findUserRoleChangesByAppAndDateTest() {

        var balanceRequest = AccountIdRequest.newBuilder()
                .setAccountId(1)
                .build();

        var accountStub = AccountResourceGrpc
                .newBlockingStub(new GrpcHttpChannel("localhost", 1234));

        AccountResponse response = accountStub.findById(balanceRequest);
        assertThat(response, notNullValue());
        assertThat(response.getAccountId(), notNullValue());
        assertThat(response.getBalance(), notNullValue());
    }

}