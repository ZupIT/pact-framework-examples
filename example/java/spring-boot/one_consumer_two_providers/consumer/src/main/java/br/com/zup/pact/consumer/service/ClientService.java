package br.com.zup.pact.consumer.service;

import br.com.zup.pact.consumer.dto.ClientDetailsDTO;
import br.com.zup.pact.consumer.dto.PrimeBalanceDTO;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<ClientDetailsDTO> getClientDetails(Integer clientId);

    Optional<List<ClientDetailsDTO>> getAll();

    Optional<PrimeBalanceDTO> getBalance(Integer clientId);
}
