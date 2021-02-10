package br.com.vinirib.pact.consumer.client.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import br.com.vinirib.pact.consumer.client.dto.BalanceDTO;
import com.google.gson.Gson;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "AccountProvider", port = "1234")
public class ClientConsumerPactTestIT {

    private static final String BALANCE_URL_WORKING = "/v1/accounts/balance/1";
    private static final String BALANCE_URL_NOT_WORKING = "/v1/accounts/balance/1000";
    private final Map<String, String> headers = MapUtils.putAll(new HashMap<>(), new String[]{
            "Content-Type", "application/json"
    });

    private final Gson gson = new Gson();

    @Pact(provider = "AccountProvider", consumer = "ClientConsumer")
    public RequestResponsePact balanceEndpointTest(PactDslWithProvider builder) {

        PactDslJsonBody bodyResponse = new PactDslJsonBody()
                .integerType("accountId")
                .integerType("clientId")
                .numberType("balance");

        return builder
                .given("get balance of an account")
                .uponReceiving("A request to " + BALANCE_URL_WORKING)
                .path(BALANCE_URL_WORKING)
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value())
                .body(bodyResponse)
                .toPact();
    }

    @Pact(provider = "AccountProvider", consumer = "ClientConsumer")
    public RequestResponsePact balanceEndpointNotWorkingTest(PactDslWithProvider builder) {
        return builder
                .given("No accounts exist from accountId 1000")
                .uponReceiving("A request to " + BALANCE_URL_NOT_WORKING)
                .path(BALANCE_URL_NOT_WORKING)
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.NOT_FOUND.value())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "balanceEndpointTest", providerName = "AccountProvider")
    void testBalanceWorking(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + BALANCE_URL_WORKING).execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(HttpStatus.OK.value())));
        final BalanceDTO balanceDTO = gson
                .fromJson(IOUtils.toString(httpResponse.getEntity().getContent()), BalanceDTO.class);
        assertThat(balanceDTO.getAccountId(), is(notNullValue()));
        assertThat(balanceDTO.getClientId(), is(notNullValue()));
        assertThat(balanceDTO.getBalance(), is(notNullValue()));
    }

    @Test
    @PactTestFor(pactMethod = "balanceEndpointNotWorkingTest", providerName = "AccountProvider")
    void testBalanceNotWorking(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + BALANCE_URL_NOT_WORKING).execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND.value())));
        assertThat(IOUtils.toString(httpResponse.getEntity().getContent()), is(equalTo("")));
    }

}
