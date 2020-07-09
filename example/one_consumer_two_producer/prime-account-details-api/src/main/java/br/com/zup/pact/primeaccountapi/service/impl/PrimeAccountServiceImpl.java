package br.com.zup.pact.primeaccountapi.service.impl;

import br.com.zup.pact.primeaccountapi.dto.PrimeAccountDetailsDTO;
import br.com.zup.pact.primeaccountapi.repository.AccountRepository;
import br.com.zup.pact.primeaccountapi.service.PrimeAccountService;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PrimeAccountServiceImpl implements PrimeAccountService {

    private final AccountRepository accountRepository;

    @Override
    public Optional<PrimeAccountDetailsDTO> getPrimeAccountDetailsByClientId(Integer clientId) {
        return accountRepository.getPrimeAccountDetailsByClientId(clientId);
    }
}
