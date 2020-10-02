package br.com.zup.pact.consumer.resource

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.service.ClientService
import io.mockk.every
import io.mockk.mockk
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

@SpringBootTest
@AutoConfigureMockMvc
class ClientResourceEndpointTest (
        @Autowired val mockMvc: MockMvc,
        @Autowired val clientService: ClientService
) {

    @TestConfiguration
    class ClientResourceEndpointTestConfig {
        @Bean
        fun clientService() = mockk<ClientService>()
    }

    @Test
    fun `Method getAll returns a ClientDetails list`() {
        val clientDetailsDTOList: List<ClientDetailsDTO> = arrayListOf(
                ClientDetailsDTO(1, 1, "any", "any", 40),
                ClientDetailsDTO(2, 2, "any", "any", 25),
                ClientDetailsDTO(3, 3, "any", "any", 20)
        )

        every { clientService.getAll() }
                .returns(clientDetailsDTOList)

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clients"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(clientDetailsDTOList.size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].clientId").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].accountId").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lastName").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].age").isNumber)
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Method getAll returns a ClientDetails empty list`() {

        every { clientService.getAll() }
                .returns(emptyList())

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clients"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").doesNotExist())
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `Method getClientDetails returns a ClientDetails`() {

        val clientDetailsDTO = ClientDetailsDTO(1,
                1, "any_first_name", "any_last_name", 40)

        every { clientService.getClientDetails(any()) }
                .returns(clientDetailsDTO)

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clients/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("any_first_name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("any_last_name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(40))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Method getClientDetails returns not found if client does not exists`() {

        every { clientService.getClientDetails(any()) }
                .returns(null)

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clients/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").doesNotExist())
                .andExpect(MockMvcResultMatchers.status().isNotFound)

    }

    @Test
    fun `Method getBalance returns a BalanceDTO`() {

        val balanceDTO = BalanceDTO(1, 1, 250.0)

        every { clientService.getBalance(any()) }
                .returns(balanceDTO)

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clients/balance/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").value(balanceDTO.accountId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").value(balanceDTO.clientId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(balanceDTO.balance))
                .andExpect(MockMvcResultMatchers.status().isOk)

    }

    @Test
    fun `Method getBalance returns not found if balance does not exists`() {

        every { clientService.getBalance(any()) }
                .returns(null)

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clients/balance/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").doesNotExist())
                .andExpect(MockMvcResultMatchers.status().isNotFound)

    }
}
