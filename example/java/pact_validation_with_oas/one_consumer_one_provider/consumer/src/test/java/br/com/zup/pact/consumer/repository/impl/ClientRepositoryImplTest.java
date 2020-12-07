package br.com.zup.pact.consumer.repository.impl;

import br.com.zup.pact.consumer.dto.ClientDetailsDTO;
import br.com.zup.pact.consumer.entity.Client;
import br.com.zup.pact.consumer.repository.ClientRepository;
import br.com.zup.pact.consumer.stub.ClientStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientRepositoryImplTest {

    @MockBean
    private ClientStub clientStub;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void findByClientIdTest() {

        when(this.clientStub.getClients())
                .thenReturn(Map.of(
                        1, this.clientListStub().get(0),
                        2, this.clientListStub().get(1)
                ));

        assertEquals(this.clientRepository.findByClientId(1),
                Optional.of(this.clientDetailsDTOStub()));
    }

    @Test
    void getAllClientsTest() {

        when(this.clientStub.getClients())
                .thenReturn(Map.of(
                        1, this.clientListStub().get(0),
                        2, this.clientListStub().get(1)
                ));

        Optional<List<ClientDetailsDTO>> actualReturn = this.clientRepository.getAll();

        assertEquals(actualReturn, Optional.of(this.clientDetailsDTOSListStub()));
    }

    private List<Client> clientListStub() {
        return Arrays.asList(
                Client.builder()
                        .id(1)
                        .accountId(1)
                        .name("Any First Name")
                        .finalName("Any Final Name")
                        .age(20)
                        .build(),
                Client.builder()
                        .id(2)
                        .accountId(2)
                        .name("Another First Name")
                        .finalName("Another Final Name")
                        .age(26)
                        .build()
        );
    }

    private List<ClientDetailsDTO> clientDetailsDTOSListStub() {
        return Arrays.asList(
                Client.fromEntityToDto(this.clientListStub().get(0)),
                Client.fromEntityToDto(this.clientListStub().get(1))
        );
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
}
