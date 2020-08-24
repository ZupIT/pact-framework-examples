package br.com.zup.pact.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrimeAccountDetailsDTO {

    private Boolean isPrime;
    private Integer discountPercentageFee;

}
