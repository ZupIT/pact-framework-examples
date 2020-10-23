package br.com.zup.pact.provider.repository;

import br.com.zup.pact.provider.dto.AccountDetailsDTO;
import br.com.zup.pact.provider.dto.BalanceDTO;
import br.com.zup.pact.provider.entity.Account;
import br.com.zup.pact.provider.exception.ClientNotFoundException;
import br.com.zup.pact.provider.stub.AccountStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;


    @Test
    void testGetBalanceByAccountIdWithNonExistingIdThrowsException() {
        Assertions.assertThrows(ClientNotFoundException.class, () -> accountRepository.getBalanceByAccountId(9999));
    }

    @Test
    void testFindAll() {
        final List<AccountDetailsDTO> allAccountsDTO = accountRepository.getAll().orElse(null);
        Assertions.assertTrue(allAccountsDTO.size() > 0);
    }

    @Test
    void testFindByAccountId() {
        final AccountDetailsDTO accountDetailsDTO = accountRepository.findByAccountId(1).orElse(null);
        Assertions.assertNotNull(accountDetailsDTO);
    }

    @Test
    void testFindByAccountIdInvalid() {
        final AccountDetailsDTO accountDetailsDTO = accountRepository.findByAccountId(9999).orElse(null);
        Assertions.assertNull(accountDetailsDTO);
    }
}
