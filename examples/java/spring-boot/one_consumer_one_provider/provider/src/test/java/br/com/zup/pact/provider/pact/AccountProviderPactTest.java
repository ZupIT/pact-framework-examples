package br.com.zup.pact.provider.pact;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.VerificationReports;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.VersionSelector;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import br.com.zup.pact.provider.dto.BalanceDTO;
import br.com.zup.pact.provider.service.AccountService;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Provider
@PactBroker(consumerVersionSelectors = {
        @VersionSelector(tag = "master", latest = "true")
})
@VerificationReports
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IgnoreNoPactsToVerify
public class AccountProviderPactTest {

    @LocalServerPort
    private int localServerPort;

    @MockBean
    private AccountService accountService;

    @BeforeEach
    void setUp(PactVerificationContext context) throws MalformedURLException {
        if (context != null)
            context.setTarget(HttpTestTarget.fromUrl(new URL("http://localhost:" + localServerPort)));
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void testTemplate(PactVerificationContext context) {
        if (context != null)
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
