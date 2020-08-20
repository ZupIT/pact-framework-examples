package br.com.zup.pact.accountapi.repository;

import br.com.zup.pact.accountapi.dto.AccountDetailsDTO;
import br.com.zup.pact.accountapi.dto.BalanceDTO;
import br.com.zup.pact.accountapi.entity.Account;
import br.com.zup.pact.accountapi.exception.ClientNotFoundException;
import br.com.zup.pact.accountapi.stub.AccountStub;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountRepository {

    private final AccountStub accountStub;

    public Optional<AccountDetailsDTO> findByClientId(Integer clientId) {
        return Optional.ofNullable(Account.fromEntityToDto(accountStub.getAccounts().get(clientId)));
    }

    public Optional<List<AccountDetailsDTO>> getAll() {
        final List<Account> accounts = accountStub.getAccounts().values().stream()
                .collect(Collectors.toList());
        List<AccountDetailsDTO> clientDetailsDTOS = new ArrayList<>();
        if (Objects.nonNull(accounts)){
            for (Account account :accounts) {
                clientDetailsDTOS.add(Account.fromEntityToDto(account));
            }
        }
        return Optional.ofNullable(clientDetailsDTOS);
    }

    public BalanceDTO getBalanceByAccountId(Integer accountId) {
        final Account accountFound = accountStub.getAccounts()
                .values()
                .stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new ClientNotFoundException("Account with id: " + accountId + " not found!"));
        return BalanceDTO.fromAccountToDTO(accountFound);
    }
}
