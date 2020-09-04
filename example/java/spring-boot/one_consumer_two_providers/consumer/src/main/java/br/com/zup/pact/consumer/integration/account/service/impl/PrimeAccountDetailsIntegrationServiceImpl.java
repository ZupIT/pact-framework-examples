package br.com.zup.pact.consumer.integration.account.service.impl;

import br.com.zup.pact.consumer.dto.PrimeAccountDetailsDTO;
import br.com.zup.pact.consumer.integration.account.service.PrimeAccountDetailsIntegrationService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PrimeAccountDetailsIntegrationServiceImpl implements PrimeAccountDetailsIntegrationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${integration.prime.account.details.url}")
    private String accountBalanceUrl;

    @Override
    public Optional<PrimeAccountDetailsDTO> getPrimeAccountDetails(Integer accountId) {
        final ResponseEntity<PrimeAccountDetailsDTO> responseEntity = restTemplate
                .getForEntity(accountBalanceUrl, PrimeAccountDetailsDTO.class, accountId);
        return Optional.ofNullable(responseEntity.getBody());
    }
}
