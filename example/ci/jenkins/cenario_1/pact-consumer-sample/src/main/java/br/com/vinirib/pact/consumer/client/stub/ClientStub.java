package br.com.vinirib.pact.consumer.client.stub;

import br.com.vinirib.pact.consumer.client.dto.ClientDetailsDTO;
import br.com.vinirib.pact.consumer.client.entity.Client;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientStub {

    private static final Integer NUMBER_OF_STUBS = 10;
    private static final Integer MIN_AGE = 18;
    private static final Integer MAX_AGE = 70;
    @Getter
    private final Map<Integer, Client> clients;

    public ClientStub() {
        log.info("\n\n\n\t\t\t\t\t\t ============================ Creating Client Stubs! ============================ \n");
        clients = createStubs(NUMBER_OF_STUBS);
    }

    private Map<Integer, Client> createStubs(int numberOfStubs) {
        final Map<Integer, Client> clients = new HashMap<>(NUMBER_OF_STUBS);
        for (int i = 1; i <= numberOfStubs; i++) {
            final Client client = Client.builder()
                    .id(i)
                    .accountId(i)
                    .age(new Random().nextInt((MAX_AGE - MIN_AGE) + 1) + MIN_AGE)
                    .name(NameStub.getRandomFirstName())
                    .finalName(NameStub.getRandomLastName())
                    .build();
            clients.put(i, client);
        }
        return clients;
    }

    public List<ClientDetailsDTO> getAllStubsDTOFormat() {
        final List<ClientDetailsDTO> clientDetailsDTOS = new ArrayList<>();
        final List<Client> clients = this.clients.values().stream()
                .collect(Collectors.toList());
        for (Client client : clients) {
            clientDetailsDTOS.add(Client.fromEntityToDto(client));
        }
        return clientDetailsDTOS;
    }
}
