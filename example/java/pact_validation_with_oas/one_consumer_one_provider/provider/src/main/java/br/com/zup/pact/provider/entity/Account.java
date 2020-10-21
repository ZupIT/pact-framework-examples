package br.com.zup.pact.provider.entity;

import br.com.zup.pact.provider.dto.AccountDetailsDTO;
import br.com.zup.pact.provider.enums.AccountType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account {

    private Integer id;
    private BigDecimal balance;
    private AccountType accountType;

    public static AccountDetailsDTO fromEntityToDto(Account account) {
        if (Objects.nonNull(account)) {
            return AccountDetailsDTO.builder()
                    .accountType(account.getAccountType())
                    .balance(account.getBalance())
                    .accountId(account.getId())
                    .build();
        }
        return null;
    }

    public static List<AccountDetailsDTO> fromEntityToDtoList(List<Account> accounts) {
        List<AccountDetailsDTO> clientDetailsDTOS = new ArrayList<>();

        if (Objects.nonNull(accounts))
            accounts
                    .stream()
                    .map(account -> Account.fromEntityToDto(account))
                    .collect(Collectors.toCollection(() -> clientDetailsDTOS));

        return clientDetailsDTOS;
    }

}
