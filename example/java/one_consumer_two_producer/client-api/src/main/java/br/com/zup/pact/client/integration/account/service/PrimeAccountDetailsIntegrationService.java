package br.com.zup.pact.client.integration.account.service;

import br.com.zup.pact.client.dto.PrimeAccountDetailsDTO;
import java.util.Optional;

public interface PrimeAccountDetailsIntegrationService {
    Optional<PrimeAccountDetailsDTO> getPrimeAccountDetails(Integer clientId);
}
