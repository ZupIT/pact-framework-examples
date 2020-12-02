package br.com.zup.pact.consumer.pact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.*;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import br.com.zup.pact.consumer.dto.BalanceDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "provider")
public class AccountPactTest {

    private static final String BALANCE_URL_WORKING = "/v1/accounts/1/balance/";
    private static final String BALANCE_URL_NOT_WORKING = "/v1/accounts/1000/balance/";
    private static final String CONSUMER_NAME = "consumer2";


    private Map<String, String> headers = MapUtils.putAll(new HashMap<>(), new String[] {
            HttpHeaders.CONTENT_TYPE,
            MediaType.APPLICATION_JSON_VALUE
    });

    private Gson gson = new Gson();

    @Pact(consumer = CONSUMER_NAME)
    public RequestResponsePact balanceEndpointTest(PactDslWithProvider builder) {
        PactDslJsonBody bodyResponse = new PactDslJsonBody()
                .integerType("accountId")
                .numberType("balance")
                .stringValue("accountType", "PERSONAL");

        return builder
                .given("get balance of accountId 1")
                .uponReceiving("A request to " + BALANCE_URL_WORKING)
                .path(BALANCE_URL_WORKING)
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(bodyResponse)
                .toPact();
    }

    @Pact(consumer = CONSUMER_NAME)
    public RequestResponsePact balanceEndpointNotWorkingTest(PactDslWithProvider builder) {
        return builder
                .given("No accounts exist from accountId 1000")
                .uponReceiving("A request to " + BALANCE_URL_NOT_WORKING)
                .path(BALANCE_URL_NOT_WORKING)
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "balanceEndpointTest")
    void testBalanceWorking(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + BALANCE_URL_WORKING).execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(200)));
    }

    @Test
    @PactTestFor(pactMethod = "balanceEndpointNotWorkingTest")
    void testBalanceNotWorking(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + BALANCE_URL_NOT_WORKING).execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(404)));
    }

}
