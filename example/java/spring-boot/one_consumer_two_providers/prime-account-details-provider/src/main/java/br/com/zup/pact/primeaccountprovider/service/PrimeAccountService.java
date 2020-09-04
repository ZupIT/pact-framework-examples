package br.com.zup.pact.primeaccountprovider.service;

import br.com.zup.pact.primeaccountprovider.dto.PrimeAccountDetailsDTO;
import java.util.Optional;

public interface PrimeAccountService {
    Optional<PrimeAccountDetailsDTO> getPrimeAccountDetailsByClientId(Integer clientId);
}
