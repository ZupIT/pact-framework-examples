package br.com.zup.pact.provider.dto;

import br.com.zup.pact.provider.entity.Account;
import java.io.Serializable;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceDTO implements Serializable {

    private Integer clientId;
    private Integer accountId;

    public static BalanceDTO fromAccountToDTO(Account accountFound) {
        if (Objects.nonNull(accountFound)) {
            return BalanceDTO.builder()
                    .accountId(accountFound.getId())
                    .clientId(accountFound.getClientId())
                    .build();

        }
        return null;
    }
}
