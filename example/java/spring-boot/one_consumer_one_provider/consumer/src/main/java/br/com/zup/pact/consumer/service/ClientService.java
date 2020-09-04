package br.com.zup.pact.consumer.service;

import br.com.zup.pact.consumer.dto.BalanceDTO;
import br.com.zup.pact.consumer.dto.ClientDetailsDTO;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<ClientDetailsDTO> getClientDetails(Integer clientId);

    Optional<List<ClientDetailsDTO>> getAll();

    Optional<BalanceDTO> getBalance(Integer clientId);
}
