package br.com.zup.pact.accountprovider.service;

import br.com.zup.pact.accountprovider.dto.AccountDetailsDTO;
import br.com.zup.pact.accountprovider.dto.BalanceDTO;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<AccountDetailsDTO> getAccountDetailsByClientId(Integer clientId);

    Optional<List<AccountDetailsDTO>> getAll();

    Optional<BalanceDTO> getBalanceByClientId(Integer clientId);
}
