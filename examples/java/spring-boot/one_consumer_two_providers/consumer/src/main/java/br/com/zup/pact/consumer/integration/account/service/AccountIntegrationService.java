package br.com.zup.pact.consumer.integration.account.service;

import br.com.zup.pact.consumer.dto.BalanceDTO;
import java.util.Optional;

public interface AccountIntegrationService {
    Optional<BalanceDTO> getBalance(Integer accountId);
}
