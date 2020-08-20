package br.com.zup.pact.legalperson.service.impl;

import br.com.zup.pact.legalperson.dto.BalanceDTO;
import br.com.zup.pact.legalperson.dto.ClientDetailsDTO;
import br.com.zup.pact.legalperson.exception.ClientNotFoundException;
import br.com.zup.pact.legalperson.integration.account.service.AccountIntegrationService;
import br.com.zup.pact.legalperson.repository.ClientRepository;
import br.com.zup.pact.legalperson.service.ClientService;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AccountIntegrationService accountIntegrationService;

    @Override
    public Optional<ClientDetailsDTO> getClientDetails(Integer clientId) {
        return clientRepository.findByClientId(clientId);
    }

    @Override
    public Optional<List<ClientDetailsDTO>> getAll() {
        return clientRepository.getAll();
    }

    @Override
    public Optional<BalanceDTO> getBalance(Integer clientId) {
        final Integer accountId = getAccountId(clientId);
        return accountIntegrationService.getBalance(accountId);
    }

    private Integer getAccountId(Integer clientId) {
        return getClientDetails(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client with id: " + clientId + " not found!"))
                .getAccountId();
    }
}
