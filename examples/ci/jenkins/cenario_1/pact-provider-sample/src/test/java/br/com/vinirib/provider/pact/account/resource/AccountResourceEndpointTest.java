package br.com.vinirib.provider.pact.account.resource;

import br.com.vinirib.provider.pact.account.dto.AccountDetailsDTO;
import br.com.vinirib.provider.pact.account.dto.BalanceDTO;
import br.com.vinirib.provider.pact.account.entity.Account;
import br.com.vinirib.provider.pact.account.service.AccountService;
import br.com.vinirib.provider.pact.account.stub.AccountStub;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountResourceEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private AccountStub accountStub;

    @Autowired
    private Gson gson;

    @Test
    void getAccountDetailsByClientId() throws Exception {
        final AccountDetailsDTO accountDetailsDTO = Account.fromEntityToDto(accountStub.getAccounts().get(1));
        when(accountService.getAccountDetailsByClientId(anyInt())).thenReturn(Optional.of(accountDetailsDTO));
        mockMvc.perform(get("/v1/accounts/1"))
                .andDo(print())
                .andExpect(content().json(gson.toJson(accountDetailsDTO)))
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
        final List<AccountDetailsDTO> accountDetailsDTOS = accountStub.getAllStubsDTOFormat();
        mockMvc.perform(get("/v1/accounts"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(accountDetailsDTOS.size())))
                .andExpect(content().json(gson.toJson(accountDetailsDTOS)))
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
        when(accountService.getBalanceByAccountId(anyInt())).thenReturn(Optional.of(balanceDTO));
        mockMvc.perform(get("/v1/accounts/balance/1"))
                .andDo(print())
                .andExpect(content().json(gson.toJson(balanceDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void getBalanceOfClientWithNoAccount() throws Exception {
        when(accountService.getBalanceByAccountId(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/v1/accounts/balance/1000"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}