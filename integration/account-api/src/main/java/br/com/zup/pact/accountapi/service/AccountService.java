package br.com.zup.pact.accountapi.service;

import br.com.zup.pact.accountapi.dto.AccountDetailsDTO;
import br.com.zup.pact.accountapi.dto.BalanceDTO;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<AccountDetailsDTO> getAccountDetailsByClientId(Integer clientId);

    Optional<List<AccountDetailsDTO>> getAll();

    BalanceDTO getBalanceByAccountId(Integer clientId);
}
