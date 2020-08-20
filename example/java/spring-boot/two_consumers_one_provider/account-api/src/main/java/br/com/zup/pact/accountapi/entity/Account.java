package br.com.zup.pact.accountapi.entity;

import br.com.zup.pact.accountapi.dto.AccountDetailsDTO;
import br.com.zup.pact.accountapi.enums.AccountType;
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
    private String name;
    private String socialReason;

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
}
