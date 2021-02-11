package br.com.vinirib.pact.consumer.client.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceDTO implements Serializable {

    private Integer clientId;
    private Integer accountId;
    private BigDecimal value;

}