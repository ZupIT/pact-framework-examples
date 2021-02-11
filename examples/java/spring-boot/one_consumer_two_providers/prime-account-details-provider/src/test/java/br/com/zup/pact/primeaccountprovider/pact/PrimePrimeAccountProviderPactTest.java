package br.com.zup.pact.primeaccountprovider.pact;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.VerificationReports;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import br.com.zup.pact.primeaccountprovider.dto.PrimeAccountDetailsDTO;
import br.com.zup.pact.primeaccountprovider.service.PrimeAccountService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Provider("PrimeAccountDetailsProvider")
@PactBroker(host = "localhost", port = "9292")
@VerificationReports
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrimePrimeAccountProviderPactTest {

    @LocalServerPort
    private int localServerPort;

    @MockBean
    private PrimeAccountService primeAccountService;

    @BeforeAll
    static void enablePublishingPact() {
        System.setProperty("pact.verifier.publishResults", "true");
    }

    @BeforeEach
    void setUp(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", localServerPort, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("get prime account details of clientId 1")
    public void getPrimeAccountDetailsDTO() {
        final PrimeAccountDetailsDTO balanceDTO = PrimeAccountDetailsDTO
                .builder()
                .isPrime(true)
                .discountPercentageFee(5)
                .build();
        given(primeAccountService.getPrimeAccountDetailsByClientId(eq(1))).willReturn(Optional.of(balanceDTO));

    }
}
