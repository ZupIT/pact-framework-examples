package br.com.zup.pact.accountapi.service.impl;

import br.com.zup.pact.accountapi.dto.AccountDetailsDTO;
import br.com.zup.pact.accountapi.dto.BalanceDTO;
import br.com.zup.pact.accountapi.repository.AccountRepository;
import br.com.zup.pact.accountapi.service.AccountService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Optional<AccountDetailsDTO> getAccountDetailsByClientId(Integer clientId) {
        return accountRepository.findByClientId(clientId);
    }

    @Override
    public Optional<List<AccountDetailsDTO>> getAll() {
        return accountRepository.getAll();
    }

    @Override
    public BalanceDTO getBalanceByAccountId(Integer clientId) {
        return accountRepository.getBalanceByAccountId(clientId);
    }
}
