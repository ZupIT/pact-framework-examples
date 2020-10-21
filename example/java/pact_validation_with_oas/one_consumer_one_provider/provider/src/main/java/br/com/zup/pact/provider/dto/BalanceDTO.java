package br.com.zup.pact.provider.dto;

import br.com.zup.pact.provider.entity.Account;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class BalanceDTO implements Serializable {

    private Integer accountId;
    private BigDecimal balance;

    public static BalanceDTO fromAccountToDTO(Account accountFound) {
        if (Objects.nonNull(accountFound)) {
            return BalanceDTO.builder()
                    .accountId(accountFound.getId())
                    .balance(accountFound.getBalance())
                    .build();

        }
        return null;
    }
}
