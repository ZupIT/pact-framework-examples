package br.com.vinirib.pact.consumer.client.service;

import br.com.vinirib.pact.consumer.client.dto.ClientDetailsDTO;
import br.com.vinirib.pact.consumer.client.dto.BalanceDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<ClientDetailsDTO> getClientDetails(Integer clientId);

    Optional<List<ClientDetailsDTO>> getAll();

    Optional<BalanceDTO> getBalance(Integer clientId);
}
