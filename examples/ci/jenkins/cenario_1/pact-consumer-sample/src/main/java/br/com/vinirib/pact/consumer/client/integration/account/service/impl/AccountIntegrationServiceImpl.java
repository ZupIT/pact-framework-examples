package br.com.vinirib.pact.consumer.client.integration.account.service.impl;

import br.com.vinirib.pact.consumer.client.dto.BalanceDTO;
import br.com.vinirib.pact.consumer.client.integration.account.service.AccountIntegrationService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountIntegrationServiceImpl implements AccountIntegrationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${integration.account.balance.url}")
    private String accountBalanceUrl;

    @Override
    public Optional<BalanceDTO> getBalance(Integer accountId) {
        final ResponseEntity<BalanceDTO> responseEntity = restTemplate.getForEntity(accountBalanceUrl, BalanceDTO.class, accountId);
        return Optional.ofNullable(responseEntity.getBody());
    }
}
