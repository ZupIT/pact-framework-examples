package br.com.zup.pact.provider.service;

import br.com.zup.pact.provider.dto.AccountDetailsDTO;
import br.com.zup.pact.provider.dto.BalanceDTO;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<AccountDetailsDTO> getAccountDetailsByAccountId(Integer clientId);

    Optional<List<AccountDetailsDTO>> getAll();

    Optional<BalanceDTO> getBalanceByAccountId(Integer clientId);
}
