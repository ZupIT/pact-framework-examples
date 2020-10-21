package br.com.zup.pact.provider.repository;

import br.com.zup.pact.provider.dto.AccountDetailsDTO;
import br.com.zup.pact.provider.dto.BalanceDTO;
import br.com.zup.pact.provider.entity.Account;
import br.com.zup.pact.provider.exception.ClientNotFoundException;
import br.com.zup.pact.provider.stub.AccountStub;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountRepository {

    private final AccountStub accountStub;

    public Optional<AccountDetailsDTO> findByAccountId(Integer accountId) {
        return Optional.ofNullable(Account.fromEntityToDto(accountStub.getAccounts().get(accountId)));
    }

    public Optional<List<AccountDetailsDTO>> getAll() {
        final List<Account> accounts = accountStub.getAccounts().values().stream()
                .collect(Collectors.toList());
        final List<AccountDetailsDTO> clientDetailsDTOS = Account.fromEntityToDtoList(accounts);
        return Optional.ofNullable(clientDetailsDTOS);
    }

    public Optional<BalanceDTO> getBalanceByAccountId(Integer accountId) {
        return Optional.ofNullable(
                BalanceDTO.fromAccountToDTO(
                    accountStub.getAccounts()
                            .values()
                            .stream()
                            .filter(account -> account.getId().equals(accountId))
                            .findFirst()
                            .orElseThrow(() -> new ClientNotFoundException("Account with id: " + accountId + " not found!"))
        ));
    }
}
