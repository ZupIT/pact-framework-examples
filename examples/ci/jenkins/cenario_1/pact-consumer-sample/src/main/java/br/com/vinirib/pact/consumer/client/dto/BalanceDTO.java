package br.com.vinirib.pact.consumer.client.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceDTO implements Serializable {

    private Integer clientId;
    private Integer accountId;
    private BigDecimal balance;

}
