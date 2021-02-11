package br.com.vinirib.provider.pact.account.entity;

import br.com.vinirib.provider.pact.account.dto.AccountDetailsDTO;
import br.com.vinirib.provider.pact.account.enums.AccountType;
import java.math.BigDecimal;
import java.util.Objects;
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
    private Integer clientId;
    private BigDecimal balance;
    private AccountType accountType;

    public static AccountDetailsDTO fromEntityToDto(Account account) {
        Objects.requireNonNull(account, "Account must not null to build AccountDetaildDTO");
        return AccountDetailsDTO.builder()
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .accountId(account.getId())
                .build();
    }
}
