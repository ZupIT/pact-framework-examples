package br.com.zup.pact.accountapi.pact;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.VerificationReports;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import br.com.zup.pact.accountapi.dto.BalanceDTO;
import br.com.zup.pact.accountapi.service.AccountService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Provider("AccountBalanceProvider")
@PactBroker(host = "localhost", port = "9292")
@VerificationReports
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountProviderPactTest {

    @LocalServerPort
    private int localServerPort;

    @MockBean
    private AccountService accountService;

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

    @State("get balance of accountId 1")
    public void getBalanceDTO() {
        final BalanceDTO balanceDTO = BalanceDTO
                .builder()
                .clientId(1)
                .accountId(1)
                .balance(new BigDecimal("100.00"))
                .build();
        given(accountService.getBalanceByClientId(eq(1))).willReturn(Optional.of(balanceDTO));

    }

    @State("No accounts exist from accountId 1000")
    public void getBalanceDTONotWorking() {
        given(accountService.getBalanceByClientId(eq(1000))).willReturn(Optional.empty());
    }
}
