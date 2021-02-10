package br.com.zup.pact.primeaccountprovider.repository;

import br.com.zup.pact.primeaccountprovider.dto.PrimeAccountDetailsDTO;
import java.util.Optional;
import java.util.Random;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountRepository {

    public Optional<PrimeAccountDetailsDTO> getPrimeAccountDetailsByClientId(Integer clientId) {
        final Random random = new Random();

        if (clientId == null) {
            return Optional.empty();
        }

        final int conditionToIsEven = 2;
        if (clientId % conditionToIsEven == 0) {
            final int maxDiscount = 25;
            return Optional.of(PrimeAccountDetailsDTO.builder()
                    .isPrime(true)
                    .discountPercentageFee(random.nextInt(maxDiscount) + 1).build()
            );
        }

        return Optional.of(PrimeAccountDetailsDTO.builder()
                .isPrime(false)
                .discountPercentageFee(0).build()
        );
    }
}
