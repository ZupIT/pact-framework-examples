package br.com.zup.pact.primeaccountapi.service;

import br.com.zup.pact.primeaccountapi.dto.PrimeAccountDetailsDTO;
import java.util.Optional;

public interface PrimeAccountService {
    Optional<PrimeAccountDetailsDTO> getPrimeAccountDetailsByClientId(Integer clientId);
}
