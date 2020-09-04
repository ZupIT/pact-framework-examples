package br.com.zup.pact.consumer.integration.account.service;

import br.com.zup.pact.consumer.dto.PrimeAccountDetailsDTO;
import java.util.Optional;

public interface PrimeAccountDetailsIntegrationService {
    Optional<PrimeAccountDetailsDTO> getPrimeAccountDetails(Integer clientId);
}
