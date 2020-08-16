package br.com.zup.pact.client.pact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import br.com.zup.pact.client.dto.PrimeAccountDetailsDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "PrimeAccountDetailsProvider", port = "1234")
public class PrimeAccountDetailsPactTest {

    private static final String BALANCE_URL_WORKING = "/v1/primeaccounts/1";
    private Map<String, String> headers = MapUtils.putAll(new HashMap<>(), new String[] {
            "Content-Type", "application/json"
    });

    private Gson gson = new Gson();

    @Pact(provider = "PrimeAccountDetailsProvider", consumer = "PrimeAccountDetailsConsumer")
    public RequestResponsePact primeBalanceEndpointTest(PactDslWithProvider builder) {

        PactDslJsonBody bodyResponse = new PactDslJsonBody()
                .integerType("accountId", 1)
                .booleanType("isPrime", true)
                .numberValue("discountPercentageFee", 5);

        return builder
                .given("get prime account details of clientId 1")
                .uponReceiving("A request to " + BALANCE_URL_WORKING)
                .path(BALANCE_URL_WORKING)
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(bodyResponse)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "primeBalanceEndpointTest", providerName = "PrimeAccountDetailsProvider")
    void testBalanceWorking(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + BALANCE_URL_WORKING).execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(200)));
        final PrimeAccountDetailsDTO primeAccountDetailsDTO = gson
                .fromJson(IOUtils.toString(httpResponse.getEntity().getContent()), PrimeAccountDetailsDTO.class);
        assertThat(primeAccountDetailsDTO.getAccountId(), is(1));
        assertThat(primeAccountDetailsDTO.getIsPrime(), is(true));
        assertThat(primeAccountDetailsDTO.getDiscountPercentageFee(), is(5));
    }

}
