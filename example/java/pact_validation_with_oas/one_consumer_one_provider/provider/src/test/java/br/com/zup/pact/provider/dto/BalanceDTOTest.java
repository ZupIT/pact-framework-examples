package br.com.zup.pact.provider.dto;

import br.com.zup.pact.provider.entity.Account;
import br.com.zup.pact.provider.enums.AccountType;
import br.com.zup.pact.provider.stub.AccountStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class BalanceDTOTest {

    @Autowired
    private AccountStub accountStub;

    @Test
    void testMapperFromAccountToDTONull() {
        final BalanceDTO balanceDTO = BalanceDTO.fromAccountToDTO(null);
        Assertions.assertNull(balanceDTO);
    }

    @Test
    void testMapperFromAccountToDTO() {
        final Account account = accountStub.getAccounts().get(1);
        final BalanceDTO balanceDTO = BalanceDTO.fromAccountToDTO(account);
        Assertions.assertTrue(balanceDTO.getAccountId() == account.getId());
        Assertions.assertTrue(balanceDTO.getBalance() == account.getBalance());
    }
}
