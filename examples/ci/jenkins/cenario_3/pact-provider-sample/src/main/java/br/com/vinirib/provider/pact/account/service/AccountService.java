package br.com.vinirib.provider.pact.account.service;

import br.com.vinirib.provider.pact.account.dto.BalanceDTO;
import br.com.vinirib.provider.pact.account.dto.AccountDetailsDTO;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<AccountDetailsDTO> getAccountDetailsByClientId(Integer clientId);

    Optional<List<AccountDetailsDTO>> getAll();

    Optional<BalanceDTO> getBalanceByAccountId(Integer clientId);
}
