package br.com.zup.pact.primeaccountapi.resource;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.zup.pact.primeaccountapi.dto.PrimeAccountDetailsDTO;
import br.com.zup.pact.primeaccountapi.service.PrimeAccountService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PrimePrimeAccountResourceEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrimeAccountService primeAccountService;

    @Test
    void getPrimeAccountDetailsByClientId() throws Exception {
        final PrimeAccountDetailsDTO accountDetailsDTO = PrimeAccountDetailsDTO.builder()
                .isPrime(true)
                .discountPercentageFee(5)
                .build();
        when(primeAccountService.getPrimeAccountDetailsByClientId(anyInt())).thenReturn(Optional.of(accountDetailsDTO));
        mockMvc.perform(get("/v1/primeaccounts/1"))
                .andDo(print())
                .andExpect(jsonPath("$.isPrime").exists())
                .andExpect(jsonPath("$.discountPercentageFee").exists())
                .andExpect(jsonPath("$.isPrime").value(accountDetailsDTO.getIsPrime()))
                .andExpect(jsonPath("$.discountPercentageFee").value(accountDetailsDTO.getDiscountPercentageFee()))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountDetailsByNonExistentClientId() throws Exception {
        mockMvc.perform(get("/v1/primeaccounts/1100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}