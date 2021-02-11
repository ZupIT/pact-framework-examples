package br.com.vinirib.provider.pact.account.dto;

import br.com.vinirib.provider.pact.account.entity.Account;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BalanceDTO implements Serializable {

    private Integer clientId;
    private Integer accountId;
    private BigDecimal value;

    public static BalanceDTO fromAccountToDTO(Account accountFound) {
        Objects.requireNonNull(accountFound, "Account must not null to build BalanceDTO");
        return BalanceDTO.builder()
                .accountId(accountFound.getId())
                .clientId(accountFound.getClientId())
                .value(accountFound.getBalance())
                .build();

    }
}
