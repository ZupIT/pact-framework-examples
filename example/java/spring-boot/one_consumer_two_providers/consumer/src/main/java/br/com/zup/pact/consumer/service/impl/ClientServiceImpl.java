package br.com.zup.pact.consumer.service.impl;

import br.com.zup.pact.consumer.dto.BalanceDTO;
import br.com.zup.pact.consumer.dto.ClientDetailsDTO;
import br.com.zup.pact.consumer.dto.PrimeAccountDetailsDTO;
import br.com.zup.pact.consumer.dto.PrimeBalanceDTO;
import br.com.zup.pact.consumer.exception.ClientNotFoundException;
import br.com.zup.pact.consumer.integration.account.service.AccountIntegrationService;
import br.com.zup.pact.consumer.integration.account.service.PrimeAccountDetailsIntegrationService;
import br.com.zup.pact.consumer.repository.ClientRepository;
import br.com.zup.pact.consumer.service.ClientService;
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
    private final PrimeAccountDetailsIntegrationService primeAccountDetailsIntegrationService;

    @Override
    public Optional<ClientDetailsDTO> getClientDetails(Integer clientId) {
        return clientRepository.findByClientId(clientId);
    }

    @Override
    public Optional<List<ClientDetailsDTO>> getAll() {
        return clientRepository.getAll();
    }

    @Override
    public Optional<PrimeBalanceDTO> getBalance(Integer clientId) {
        final Integer accountId = getAccountId(clientId);
        final Optional<BalanceDTO> balanceDTOOptional = accountIntegrationService.getBalance(accountId);

        if (balanceDTOOptional.isEmpty()) {
            return Optional.empty();
        }

        final BalanceDTO balanceDTO = balanceDTOOptional.get();

        final Optional<PrimeAccountDetailsDTO> primeAccountDetailsDTOOptional = primeAccountDetailsIntegrationService
                .getPrimeAccountDetails(balanceDTO.getClientId());

        if (primeAccountDetailsDTOOptional.isEmpty()) {
            return Optional.of(PrimeBalanceDTO.fromBalanceDTO(balanceDTO));
        }

        final PrimeAccountDetailsDTO primeAccountDetailsDTO = primeAccountDetailsDTOOptional.get();

        final PrimeBalanceDTO primeBalanceDTO = PrimeBalanceDTO.fromBalanceDTO(
                balanceDTO,
                primeAccountDetailsDTO.getIsPrime(),
                primeAccountDetailsDTO.getDiscountPercentageFee()
        );

        return Optional.of(primeBalanceDTO);

    }

    private Integer getAccountId(Integer clientId) {
        return getClientDetails(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client with id: " + clientId + " not found!"))
                .getAccountId();
    }
}
