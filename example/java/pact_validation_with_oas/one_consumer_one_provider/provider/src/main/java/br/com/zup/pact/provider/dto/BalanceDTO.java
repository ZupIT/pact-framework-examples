package br.com.zup.pact.provider.dto;

import br.com.zup.pact.provider.entity.Account;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import br.com.zup.pact.provider.enums.AccountType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class BalanceDTO implements Serializable {

    private Integer accountId;
    private BigDecimal balance;
    private AccountType accountType;

    public static BalanceDTO fromAccountToDTO(Account accountFound) {
        if (Objects.nonNull(accountFound)) {
            return BalanceDTO.builder()
                    .accountId(accountFound.getId())
                    .balance(accountFound.getBalance())
                    .accountType(accountFound.getAccountType())
                    .build();
        }
        return null;
    }
}
