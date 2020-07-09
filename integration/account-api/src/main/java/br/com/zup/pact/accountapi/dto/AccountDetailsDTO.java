package br.com.zup.pact.accountapi.dto;

import br.com.zup.pact.accountapi.entity.Account;
import br.com.zup.pact.accountapi.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Builder
public class AccountDetailsDTO {

    private Integer accountId;
    private BigDecimal balance;
    private AccountType accountType;


    public static Account fromDtoToEntity(AccountDetailsDTO accountDetailsDTO) {
        if (Objects.nonNull(accountDetailsDTO)){
            return Account.builder()
                    .accountType(accountDetailsDTO.getAccountType())
                    .balance(accountDetailsDTO.getBalance())
                    .build();

        }
        return null;
    }
}
