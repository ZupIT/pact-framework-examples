package br.com.zup.pact.provider.resource;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.zup.pact.provider.dto.AccountDetailsDTO;
import br.com.zup.pact.provider.dto.BalanceDTO;
import br.com.zup.pact.provider.entity.Account;
import br.com.zup.pact.provider.service.AccountService;
import br.com.zup.pact.provider.stub.AccountStub;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AccountResourceEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private AccountStub accountStub;

    @Test
    void getAccountDetailsByClientId() throws Exception {
        final AccountDetailsDTO accountDetailsDTO = Account.fromEntityToDto(accountStub.getAccounts().get(1));
        when(accountService.getAccountDetailsByClientId(anyInt())).thenReturn(Optional.of(accountDetailsDTO));
        mockMvc.perform(get("/v1/accounts/1"))
                .andDo(print())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.accountType").exists())
                .andExpect(jsonPath("$.accountId").value(accountDetailsDTO.getAccountId()))
                .andExpect(jsonPath("$.balance").value(accountDetailsDTO.getBalance()))
                .andExpect(jsonPath("$.accountType").value(accountDetailsDTO.getAccountType().toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountDetailsByNonExistentClientId() throws Exception {
        mockMvc.perform(get("/v1/accounts/1100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        when(accountService.getAll()).thenReturn(Optional.of(accountStub.getAllStubsDTOFormat()));
        final AccountDetailsDTO firstAccountDetailsDTO = accountStub.getAllStubsDTOFormat().get(0);
        mockMvc.perform(get("/v1/accounts"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(accountStub.getAllStubsDTOFormat().size())))
                .andExpect(jsonPath("$.[0].accountId").exists())
                .andExpect(jsonPath("$.[0].balance").exists())
                .andExpect(jsonPath("$.[0].accountType").exists())
                .andExpect(jsonPath("$.[0].accountId").value(firstAccountDetailsDTO.getAccountId()))
                .andExpect(jsonPath("$.[0].accountType").value(firstAccountDetailsDTO.getAccountType().toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getAllNoContent() throws Exception {
        when(accountService.getAll()).thenReturn(Optional.empty());
        mockMvc.perform(get("/v1/accounts"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getBalanceOfClientId() throws Exception {
        final BalanceDTO balanceDTO = BalanceDTO.fromAccountToDTO(accountStub.getAccounts().get(1));
        when(accountService.getBalanceByClientId(anyInt())).thenReturn(Optional.of(balanceDTO));
        mockMvc.perform(get("/v1/accounts/balance/1"))
                .andDo(print())
                .andExpect(jsonPath("$.clientId").exists())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.clientId").value(balanceDTO.getClientId()))
                .andExpect(jsonPath("$.accountId").value(balanceDTO.getAccountId()))
                .andExpect(status().isOk());
    }

    @Test
    void getBalanceOfClientWithNoAccount() throws Exception {
        when(accountService.getBalanceByClientId(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/v1/accounts/balance/1000"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}