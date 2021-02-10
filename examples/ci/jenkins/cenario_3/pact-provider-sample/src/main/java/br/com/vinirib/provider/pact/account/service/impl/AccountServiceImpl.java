package br.com.vinirib.provider.pact.account.service.impl;

import br.com.vinirib.provider.pact.account.dto.BalanceDTO;
import br.com.vinirib.provider.pact.account.repository.AccountRepository;
import br.com.vinirib.provider.pact.account.service.AccountService;
import br.com.vinirib.provider.pact.account.dto.AccountDetailsDTO;
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
    public Optional<BalanceDTO> getBalanceByAccountId(Integer accountId) {
        return accountRepository.getBalanceByAccountId(accountId);
    }
}
