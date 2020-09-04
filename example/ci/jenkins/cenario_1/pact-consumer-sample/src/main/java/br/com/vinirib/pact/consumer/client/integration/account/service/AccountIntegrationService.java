package br.com.vinirib.pact.consumer.client.integration.account.service;

import br.com.vinirib.pact.consumer.client.dto.BalanceDTO;
import java.util.Optional;

public interface AccountIntegrationService {
    Optional<BalanceDTO> getBalance(Integer accountId);
}
