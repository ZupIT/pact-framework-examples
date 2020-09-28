package br.com.zup.pact.provider.pact

import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.VerificationReports
import au.com.dius.pact.provider.junitsupport.loader.PactBroker
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.service.AccountService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URL

@Provider("AccountBalanceProvider")
@PactBroker(host = "localhost", port = "9292")
@VerificationReports
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountProviderPactTest {
    @Autowired
    private lateinit var accountService: AccountService

    @LocalServerPort
    private var localPort: Int? = null

    @TestConfiguration
    class AccountResourceEndpointTestConfig {
        @Bean
        fun accountService() = mockk<AccountService>()
    }

    @BeforeEach
    fun setUp(context: PactVerificationContext) {
        context.target = HttpTestTarget.fromUrl(URL("http://localhost:$localPort"))
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider::class)
    fun testTemplate(context: PactVerificationContext) {
        context.verifyInteraction()
    }

    @State("No accounts exist from accountId 1000")
    fun getBalanceDTONotWorking() {
        every { accountService.getBalanceByClientId(1000) }
                .returns(null)
    }

    @State("get balance of accountId 1")
    fun getBalanceDTO() {
        val accountId = 1
        val clientId = 1
        val balance = 100.0
        val balanceDTO = BalanceDTO(accountId, clientId, balance)
        every { accountService.getBalanceByClientId(1) }
                .returns(balanceDTO)
    }
}
