package br.com.zup.pact.consumer.repository.impl;

import br.com.zup.pact.consumer.dto.ClientDetailsDTO;
import br.com.zup.pact.consumer.entity.Client;
import br.com.zup.pact.consumer.repository.ClientRepository;
import br.com.zup.pact.consumer.stub.ClientStub;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientStub clientStub;

    @Override
    public Optional<ClientDetailsDTO> findByClientId(Integer clientId) {
        return Optional.ofNullable(Client.fromEntityToDto(clientStub.getClients().get(clientId)));
    }

    @Override
    public Optional<List<ClientDetailsDTO>> getAll() {
        final List<Client> clients = clientStub.getClients().values().stream()
                .collect(Collectors.toList());
        final List<ClientDetailsDTO> clientDetailsDTOS = new ArrayList<>();
        if (Objects.nonNull(clients)) {
            for (Client client : clients) {
                clientDetailsDTOS.add(Client.fromEntityToDto(client));
            }
        }
        return Optional.ofNullable(clientDetailsDTOS);
    }
}
