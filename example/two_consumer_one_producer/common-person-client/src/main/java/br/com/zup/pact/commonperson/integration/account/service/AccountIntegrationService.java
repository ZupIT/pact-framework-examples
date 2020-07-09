package br.com.zup.pact.commonperson.integration.account.service;

import br.com.zup.pact.commonperson.dto.BalanceDTO;
import java.util.Optional;

public interface AccountIntegrationService {
    Optional<BalanceDTO> getBalance(Integer accountId);
}
