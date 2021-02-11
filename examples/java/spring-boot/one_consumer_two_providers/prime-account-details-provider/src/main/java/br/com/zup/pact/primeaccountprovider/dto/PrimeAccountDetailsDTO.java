package br.com.zup.pact.primeaccountprovider.dto;

import br.com.zup.pact.primeaccountprovider.entity.PrimeAccount;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrimeAccountDetailsDTO {

    private Boolean isPrime;
    private Integer discountPercentageFee;


    public static PrimeAccount fromDtoToEntity(PrimeAccountDetailsDTO accountDetailsDTO) {
        if (Objects.nonNull(accountDetailsDTO)) {
            return PrimeAccount.builder()
                    .isPrime(accountDetailsDTO.getIsPrime())
                    .discountPercentageFee(accountDetailsDTO.getDiscountPercentageFee())
                    .build();

        }
        return null;
    }
}
