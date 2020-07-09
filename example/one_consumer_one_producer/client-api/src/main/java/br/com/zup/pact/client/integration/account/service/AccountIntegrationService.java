package br.com.zup.pact.client.integration.account.service;

import br.com.zup.pact.client.dto.BalanceDTO;
import java.util.Optional;

public interface AccountIntegrationService {
    Optional<BalanceDTO> getBalance(Integer accountId);
}
