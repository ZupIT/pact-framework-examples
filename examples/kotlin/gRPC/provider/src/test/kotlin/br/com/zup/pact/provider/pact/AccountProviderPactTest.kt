package br.com.zup.pact.provider.pact


import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.VerificationReports
import au.com.dius.pact.provider.junitsupport.loader.PactBroker
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider
import br.com.zup.pact.provider.ProviderApplication
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.service.AccountService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.MalformedURLException
import java.net.URL

@Provider("grpc-account-provider")
@PactBroker
@VerificationReports
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [ProviderApplication::class], webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles(profiles = ["test"])
class AccountProviderPactTest {
    @Value("\${local.server.port}")
    protected var localPort = 0

    @MockBean
    private lateinit var accountService: AccountService

    @BeforeEach
    @Throws(MalformedURLException::class)
    fun setUp(context: PactVerificationContext) {
        context.target = HttpTestTarget.fromUrl(URL("http://localhost:$localPort"))
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider::class)
    fun testTemplate(context: PactVerificationContext) {
        context.verifyInteraction()
    }

    @State("get balance of accountId 1")
    fun getBalanceDTO() {
        val balanceDTO = BalanceDTO(1, 100.0)
        BDDMockito.given(accountService.findByAccountId(ArgumentMatchers.eq(1))).willReturn(balanceDTO)
    }
}
