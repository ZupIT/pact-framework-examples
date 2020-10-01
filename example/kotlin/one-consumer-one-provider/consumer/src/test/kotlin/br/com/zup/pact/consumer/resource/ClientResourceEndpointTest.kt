package br.com.zup.pact.consumer.resource

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
    fun getAllClientsDetailsList() {
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
}
