package br.com.zup.pact.provider.dto;

import br.com.zup.pact.provider.entity.Account;
import br.com.zup.pact.provider.enums.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class BalanceDTOTest {

    @Test
    void testMapperFromAccountToDTO() {

        final BalanceDTO balanceDTO = BalanceDTO.fromAccountToDTO(null);

        Assertions.assertTrue(balanceDTO == isNull());
    }
}
