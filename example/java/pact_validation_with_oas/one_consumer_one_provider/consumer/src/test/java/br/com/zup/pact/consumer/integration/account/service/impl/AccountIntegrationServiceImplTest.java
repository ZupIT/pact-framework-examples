package br.com.zup.pact.consumer.integration.account.service.impl;

import br.com.zup.pact.consumer.dto.BalanceDTO;
import br.com.zup.pact.consumer.integration.account.service.AccountIntegrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountIntegrationServiceImplTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private AccountIntegrationService accountIntegrationService;

    @Value("${integration.account.balance.url}")
    private String accountBalanceUrl;

    @Test
    void getBalanceTest() {

        BalanceDTO balanceDTO = BalanceDTO.builder()
                .accountId(1)
                .clientId(1)
                .balance(new BigDecimal("100.0"))
                .build();

        when(this.restTemplate.getForEntity(
                accountBalanceUrl, BalanceDTO.class, 1))
                .thenReturn(ResponseEntity.ok(balanceDTO));

        Optional<BalanceDTO> actualReturn = this.accountIntegrationService.getBalance(1);

        assertEquals(actualReturn, Optional.of(balanceDTO));
    }

    @Test
    void getBalanceNotFoundTest() {

        when(this.restTemplate.getForEntity(
                accountBalanceUrl, BalanceDTO.class, 2))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        BalanceDTO actualReturn = this.accountIntegrationService.getBalance(2)
                .orElse(null);

        assertNull(actualReturn);
    }
}
