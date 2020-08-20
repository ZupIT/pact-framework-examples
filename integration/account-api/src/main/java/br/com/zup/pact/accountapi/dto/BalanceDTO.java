package br.com.zup.pact.accountapi.dto;

import br.com.zup.pact.accountapi.entity.Account;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class BalanceDTO implements Serializable {

    private Integer clientId;
    private Integer accountId;
    private BigDecimal balance;

    public static BalanceDTO fromAccountToDTO(Account accountFound) {
        return BalanceDTO.builder()
                .accountId(accountFound.getId())
                .clientId(accountFound.getClientId())
                .balance(accountFound.getBalance())
                .build();

    }
}