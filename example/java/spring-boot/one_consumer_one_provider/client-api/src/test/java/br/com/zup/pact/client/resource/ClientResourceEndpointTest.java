package br.com.zup.pact.client.resource;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.zup.pact.client.dto.BalanceDTO;
import br.com.zup.pact.client.dto.ClientDetailsDTO;
import br.com.zup.pact.client.entity.Client;
import br.com.zup.pact.client.service.ClientService;
import br.com.zup.pact.client.stub.ClientStub;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ClientResourceEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ClientStub clientStub;

    @Test
    void getClientDetails() throws Exception {
        final ClientDetailsDTO clientDetailsDTO = Client.fromEntityToDto(clientStub.getClients().get(1));
        when(clientService.getClientDetails(anyInt())).thenReturn(Optional.of(clientDetailsDTO));
        mockMvc.perform(get("/v1/clients/1"))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.finalName").exists())
                .andExpect(jsonPath("$.age").exists())
                .andExpect(jsonPath("$.id").value(clientDetailsDTO.getId()))
                .andExpect(jsonPath("$.name").value(clientDetailsDTO.getName()))
                .andExpect(jsonPath("$.finalName").value(clientDetailsDTO.getFinalName()))
                .andExpect(jsonPath("$.age").value(clientDetailsDTO.getAge()))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountDetailsByNonExistentClientId() throws Exception {
        mockMvc.perform(get("/v1/clients/1000"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        when(clientService.getAll()).thenReturn(Optional.of(clientStub.getAllStubsDTOFormat()));
        final ClientDetailsDTO firstClientDetailsDTO = clientStub.getAllStubsDTOFormat().get(0);
        mockMvc.perform(get("/v1/clients"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(clientStub.getAllStubsDTOFormat().size())))
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].name").exists())
                .andExpect(jsonPath("$.[0].finalName").exists())
                .andExpect(jsonPath("$.[0].age").exists())
                .andExpect(jsonPath("$.[0].id").value(firstClientDetailsDTO.getId()))
                .andExpect(jsonPath("$.[0].name").value(firstClientDetailsDTO.getName()))
                .andExpect(jsonPath("$.[0].finalName").value(firstClientDetailsDTO.getFinalName()))
                .andExpect(jsonPath("$.[0].age").value(firstClientDetailsDTO.getAge()))
                .andExpect(status().isOk());
    }

    @Test
    void getAllNoContent() throws Exception {
        when(clientService.getAll()).thenReturn(Optional.empty());
        mockMvc.perform(get("/v1/clients"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getBalance() throws Exception {
        BalanceDTO balanceDTO = buildBalanceDTO();
        when(clientService.getBalance(anyInt())).thenReturn(Optional.of(balanceDTO));
        mockMvc.perform(get("/v1/clients/balance/1"))
                .andDo(print())
                .andExpect(jsonPath("$.clientId").exists())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.clientId").value(balanceDTO.getClientId()))
                .andExpect(jsonPath("$.accountId").value(balanceDTO.getAccountId()))
                .andExpect(jsonPath("$.balance").value(balanceDTO.getBalance()))
                .andExpect(status().isOk());
    }

    private BalanceDTO buildBalanceDTO() {
        return BalanceDTO.builder()
                .accountId(1)
                .balance("mil reais")
                .clientId(10)
                .build();
    }
}