package br.com.zup.pact.provider.resource

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.enum.AccountType
import br.com.zup.pact.provider.service.AccountService
import io.mockk.Matcher
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
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

    @Test
    fun getAccountDetailsByNonExistentClientId() {
        every { accountService.getAccountDetailsByClientId(any()) }
                .returns(Optional.empty())
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().`is`(404))
    }

    @Test
    fun getAll() {
        val accountDetailsDTO1 = AccountDetailsDTO(1, 5.0, AccountType.COMMON)
        val accountDetailsDTO2 = AccountDetailsDTO(2, 6.0, AccountType.COMMON)
        val accountDetailsDTO3 = AccountDetailsDTO(3, 8.0, AccountType.COMMON)
        val accountDetailsDTO4 = AccountDetailsDTO(4, 90.0, AccountType.COMMON)

        val accountDetailsDTOList: List<AccountDetailsDTO> = arrayListOf(
                accountDetailsDTO1, accountDetailsDTO2, accountDetailsDTO3, accountDetailsDTO4)

        every { accountService.getAll() }
                .returns(accountDetailsDTOList)
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(accountDetailsDTOList.size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].accountId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].balance").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].accountType").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].accountId").value(accountDetailsDTO1.accountId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].balance").value(accountDetailsDTO1.balance))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].accountType").value(accountDetailsDTO1.accountType.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun getBalanceByClientId() {
        val accountId = 1
        val clientId = 1
        val balance = 10.0
        val balanceDTO = BalanceDTO(accountId, clientId, balance)

        every { accountService.getBalanceByClientId(any()) }
                .returns(Optional.ofNullable(balanceDTO))
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts/balance/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").value(accountId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").value(clientId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(balance))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

}
