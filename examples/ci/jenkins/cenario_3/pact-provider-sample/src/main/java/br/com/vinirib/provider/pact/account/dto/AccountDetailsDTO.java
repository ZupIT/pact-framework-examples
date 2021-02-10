package br.com.vinirib.provider.pact.account.dto;

import br.com.vinirib.provider.pact.account.entity.Account;
import br.com.vinirib.provider.pact.account.enums.AccountType;
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
        Objects.requireNonNull(accountDetailsDTO, "AccountDetailsDTO must not null to build Account");
        return Account.builder()
                .accountType(accountDetailsDTO.getAccountType())
                .balance(accountDetailsDTO.getBalance())
                .build();

    }
}
