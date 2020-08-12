package br.com.zup.pact.client.service;

import br.com.zup.pact.client.dto.ClientDetailsDTO;
import br.com.zup.pact.client.dto.PrimeBalanceDTO;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<ClientDetailsDTO> getClientDetails(Integer clientId);

    Optional<List<ClientDetailsDTO>> getAll();

    Optional<PrimeBalanceDTO> getBalance(Integer clientId);
}
