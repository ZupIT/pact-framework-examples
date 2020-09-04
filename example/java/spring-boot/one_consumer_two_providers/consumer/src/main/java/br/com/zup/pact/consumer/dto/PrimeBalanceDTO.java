package br.com.zup.pact.consumer.dto;

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
public class PrimeBalanceDTO implements Serializable {

    private Integer clientId;
    private Integer accountId;
    private BigDecimal balance;
    private Boolean isPrime;
    private Integer discountPercentageFee;

    public static PrimeBalanceDTO fromBalanceDTO(
            BalanceDTO balanceDTO,
            Boolean isPrime,
            Integer discountPercentageFee
    ) {
        final PrimeBalanceDTO primeBalanceDTO = PrimeBalanceDTO.fromBalanceDTO(balanceDTO);

        primeBalanceDTO.setIsPrime(isPrime);
        primeBalanceDTO.setDiscountPercentageFee(discountPercentageFee);

        return primeBalanceDTO;
    }

    public static PrimeBalanceDTO fromBalanceDTO(BalanceDTO balanceDTO) {
        final PrimeBalanceDTO primeBalanceDTO = PrimeBalanceDTO.builder()
                .clientId(balanceDTO.getClientId())
                .accountId(balanceDTO.getAccountId())
                .balance(balanceDTO.getBalance())
                .build();

        return primeBalanceDTO;
    }

}
