package br.com.zup.pact.consumer.repository.impl;

import br.com.zup.pact.consumer.dto.ClientDetailsDTO;
import br.com.zup.pact.consumer.entity.Client;
import br.com.zup.pact.consumer.repository.ClientRepository;
import br.com.zup.pact.consumer.stub.ClientStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
                        1, this.clientStub(1, 1),
                        2, this.clientStub(2, 2)
                ));

        assertEquals(this.clientRepository.findByClientId(1),
                Optional.of(this.clientDetailsDTOStub(1,1)));
    }

    @Test
    void getAllClientsTest() {

        when(this.clientStub.getClients())
                .thenReturn(Map.of(
                        1, this.clientStub(1, 1),
                        2, this.clientStub(2, 2)
                ));

        Optional<List<ClientDetailsDTO>> actualReturn = this.clientRepository.getAll();

        assertNotNull(actualReturn);
    }

    private ClientDetailsDTO clientDetailsDTOStub(int id, int accountId) {
        return ClientDetailsDTO.builder()
                .id(id)
                .accountId(accountId)
                .name("Any First Name")
                .finalName("Any Final Name")
                .age(20)
                .build();
    }

    private Client clientStub(int id, int accountId) {
        return Client.builder()
                .id(id)
                .accountId(accountId)
                .name("Any First Name")
                .finalName("Any Final Name")
                .age(20)
                .build();
    }
}
