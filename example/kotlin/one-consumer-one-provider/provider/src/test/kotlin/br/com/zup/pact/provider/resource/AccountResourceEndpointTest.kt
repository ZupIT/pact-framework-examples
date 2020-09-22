package br.com.zup.pact.provider.resource

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.enum.AccountType
import br.com.zup.pact.provider.service.AccountService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class AccountResourceEndpointTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val accountService: AccountService
) {

    @TestConfiguration
    class AccountResourceEndpointTestConfig {
        @Bean
        fun accountService() = mockk<AccountService>()
    }

    @Test
    fun getAccountDetailsByClientId() {
        val accountId = 1
        val balance = 2.0
        val accountType = AccountType.COMMON
        val accountDetailsDTO = AccountDetailsDTO(accountId, balance, accountType)
        every { accountService.getAccountDetailsByClientId(any()) }
                .returns(Optional.ofNullable(accountDetailsDTO))

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountType").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").value(accountId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(balance))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountType").value(accountType.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }
}
