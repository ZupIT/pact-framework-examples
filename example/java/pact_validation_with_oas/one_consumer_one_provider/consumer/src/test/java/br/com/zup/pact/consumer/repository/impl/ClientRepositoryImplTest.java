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
                        1, this.client(),
                        2, this.client()
                ));

        assertEquals(this.clientRepository.findByClientId(1),
                Optional.of(this.clientDetailsDTOStub()));
    }

    @Test
    void getAllClientsTest() {

        when(this.clientStub.getClients())
                .thenReturn(Map.of(
                        1, this.client(),
                        2, this.client()
                ));

        Optional<List<ClientDetailsDTO>> actualReturn = this.clientRepository.getAll();

        assertEquals(actualReturn, Optional.of(this.clientDetailsDTOSListStub()));
    }

    private Client client() {
        return Client.builder()
                .id(1)
                .accountId(1)
                .name("Any First Name")
                .finalName("Any Final Name")
                .age(20)
                .build();
    }

    private List<ClientDetailsDTO> clientDetailsDTOSListStub() {
        return Arrays.asList(this.clientDetailsDTOStub(), this.clientDetailsDTOStub());
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
