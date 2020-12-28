package br.com.zup.pact.consumer.repository;

import br.com.zup.pact.consumer.dto.ClientDetailsDTO;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<List<ClientDetailsDTO>> getAll();
    Optional<ClientDetailsDTO> findByClientId(Integer clientId);
}
