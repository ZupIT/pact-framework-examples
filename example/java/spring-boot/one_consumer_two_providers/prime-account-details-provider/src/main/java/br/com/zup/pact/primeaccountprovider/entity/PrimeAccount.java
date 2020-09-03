package br.com.zup.pact.primeaccountprovider.entity;

import br.com.zup.pact.primeaccountprovider.dto.PrimeAccountDetailsDTO;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PrimeAccount {

    private Boolean isPrime;
    private Integer discountPercentageFee;

    public static PrimeAccountDetailsDTO fromEntityToDto(PrimeAccount primeAccount) {
        if (Objects.nonNull(primeAccount)) {
            return PrimeAccountDetailsDTO.builder()
                    .isPrime(primeAccount.getIsPrime())
                    .discountPercentageFee(primeAccount.getDiscountPercentageFee())
                    .build();
        }
        return null;
    }
}
