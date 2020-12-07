package br.com.zup.pact.consumer.service.impl;

import br.com.zup.pact.consumer.dto.BalanceDTO;
import br.com.zup.pact.consumer.dto.ClientDetailsDTO;
import br.com.zup.pact.consumer.integration.account.service.AccountIntegrationService;
import br.com.zup.pact.consumer.repository.ClientRepository;
import br.com.zup.pact.consumer.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceImplTest {

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private AccountIntegrationService accountIntegrationService;

    @Autowired
    private ClientService clientService;

    @Test
    void getClientDetailsTest() {

        when(this.clientRepository.findByClientId(anyInt()))
                .thenReturn(Optional.of(this.clientDetailsDTOStub()));

        assertEquals(this.clientService.getClientDetails(1), Optional.of(this.clientDetailsDTOStub()));
    }

    @Test
    void getAllClientsDetailsListTest() {

        when(this.clientRepository.getAll())
                .thenReturn(Optional.of(this.clientDetailsDTOStubList()));

        assertEquals(this.clientService.getAll(), Optional.of(this.clientDetailsDTOStubList()));
    }

    @Test
    void getBalanceTest() {
        final BalanceDTO balanceDTO = BalanceDTO.builder()
                .accountId(1)
                .clientId(1)
                .balance(new BigDecimal("100.0"))
                .build();

        when(this.clientRepository.findByClientId(anyInt()))
                .thenReturn(Optional.of(this.clientDetailsDTOStub()));

        when(this.accountIntegrationService.getBalance(anyInt()))
                .thenReturn(Optional.of(balanceDTO));

        assertEquals(this.clientService.getBalance(1), Optional.of(balanceDTO));
    }

    private ClientDetailsDTO clientDetailsDTOStub() {
        return ClientDetailsDTO.builder()
                .id(1)
                .accountId(1)
                .name("Any First Name")
                .finalName("Any Final Name")
                .age(20)
                .build();
    }

    private List<ClientDetailsDTO> clientDetailsDTOStubList() {
        return Arrays.asList(
                ClientDetailsDTO.builder()
                        .id(1)
                        .accountId(1)
                        .name("Any First Name")
                        .finalName("Any Final Name")
                        .age(20)
                        .build(),
                ClientDetailsDTO.builder()
                        .id(2)
                        .accountId(2)
                        .name("Another First Name")
                        .finalName("Another Final Name")
                        .age(26)
                        .build()
        );
    }
}
