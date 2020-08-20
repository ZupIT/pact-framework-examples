package br.com.zup.pact.client.repository;

import br.com.zup.pact.client.dto.ClientDetailsDTO;
import br.com.zup.pact.client.entity.Client;
import br.com.zup.pact.client.stub.ClientStub;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientRepository {

    private final ClientStub clientStub;

    public Optional<ClientDetailsDTO> findByClientId(Integer clientId) {
        return Optional.ofNullable(Client.fromEntityToDto(clientStub.getClients().get(clientId)));
    }

    public Optional<List<ClientDetailsDTO>> getAll() {
        final List<Client> clients = clientStub.getClients().values().stream()
                .collect(Collectors.toList());
        List<ClientDetailsDTO> clientDetailsDTOS = new ArrayList<>();
        if (Objects.nonNull(clients)){
            for (Client client :clients) {
                clientDetailsDTOS.add(Client.fromEntityToDto(client));
            }
        }
        return Optional.ofNullable(clientDetailsDTOS);
    }
}
