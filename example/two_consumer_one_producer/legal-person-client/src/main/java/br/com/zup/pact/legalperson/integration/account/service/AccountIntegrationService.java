package br.com.zup.pact.legalperson.integration.account.service;

import br.com.zup.pact.legalperson.dto.BalanceDTO;
import java.util.Optional;

public interface AccountIntegrationService {
    Optional<BalanceDTO> getBalance(Integer accountId);
}
