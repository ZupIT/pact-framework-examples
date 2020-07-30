package br.com.zup.pact.commonperson.service;

import br.com.zup.pact.commonperson.dto.BalanceDTO;
import br.com.zup.pact.commonperson.dto.ClientDetailsDTO;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<ClientDetailsDTO> getClientDetails(Integer clientId);

    Optional<List<ClientDetailsDTO>> getAll();

    Optional<BalanceDTO> getBalance(Integer clientId);
}
