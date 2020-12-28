package br.com.zup.pact.provider.dto;

import br.com.zup.pact.provider.entity.Account;
import br.com.zup.pact.provider.enums.AccountType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class AccountDetailsDTO {

    private Integer accountId;
    private BigDecimal balance;
    private AccountType accountType;

    public static class AccoutDetailsWrapper extends HashMap<String, AccountDetailsDTO> {


    }
}
