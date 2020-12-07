package br.com.zup.pact.consumer.stub;

import br.com.zup.pact.consumer.dto.ClientDetailsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClientStubTest {

    @Autowired
    private ClientStub clientStub;

    private static final int NUMBER_OF_STUBS = 10;

    @Test
    void getClientsDetailsDTOStubsListTest() {
        List<ClientDetailsDTO> clientDetailsDTOList = clientStub.getAllStubsDTOFormat();

        clientDetailsDTOList.forEach(clientDetailsDTO -> {
            assertNotNull(clientDetailsDTO.getName());
            assertNotNull(clientDetailsDTO.getFinalName());
            assertNotNull(clientDetailsDTO.getAge());
            assertNotNull(clientDetailsDTO.getAccountId());
            assertNotNull(clientDetailsDTO.getId());
        });

        assertEquals(NUMBER_OF_STUBS, clientDetailsDTOList.size());
    }
}
