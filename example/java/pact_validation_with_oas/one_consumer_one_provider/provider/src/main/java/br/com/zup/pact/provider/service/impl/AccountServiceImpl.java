package br.com.zup.pact.provider.service.impl;

import br.com.zup.pact.provider.dto.AccountDetailsDTO;
import br.com.zup.pact.provider.dto.BalanceDTO;
import br.com.zup.pact.provider.repository.AccountRepository;
import br.com.zup.pact.provider.service.AccountService;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Optional<AccountDetailsDTO> getAccountDetailsByAccountId(Integer accountId) {
        return accountRepository.findByAccountId(accountId);
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
