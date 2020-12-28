package br.com.zup.pact.provider.service;

import br.com.zup.pact.provider.dto.AccountDetailsDTO;
import br.com.zup.pact.provider.dto.BalanceDTO;
import br.com.zup.pact.provider.entity.Account;
import br.com.zup.pact.provider.exception.ClientNotFoundException;
import br.com.zup.pact.provider.repository.AccountRepository;
import br.com.zup.pact.provider.service.impl.AccountServiceImpl;
import br.com.zup.pact.provider.stub.AccountStub;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceImplTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountStub accountStub;

    @Test
    void testGetAccountDetailsByAccountIdValid() {
        final AccountDetailsDTO accountDetailsDTOMock = Account.fromEntityToDto(accountStub.getAccounts().get(1));
        when(accountRepository.findByAccountId(anyInt())).thenReturn(Optional.of(accountDetailsDTOMock));

        final AccountDetailsDTO accountDetailsDTOActual = accountService.getAccountDetailsByAccountId(anyInt()).orElse(null);

        Assert.assertTrue(new ReflectionEquals(accountDetailsDTOMock).matches(accountDetailsDTOActual));

    }

    @Test
    void testGetAccountDetailsByAccountIdInvalid() {
        when(accountRepository.findByAccountId(anyInt()))
                    .thenThrow(ClientNotFoundException.class);

        Assertions.assertThrows(ClientNotFoundException.class, () -> accountService.getAccountDetailsByAccountId(anyInt()));

    }

    @Test
    void testGetAllAccounts() {
        final List<AccountDetailsDTO> allAccountsDTOMock = Account.fromEntityToDtoList(
                new ArrayList<>(accountStub.getAccounts().values())
        );
        when(accountRepository.getAll()).thenReturn(Optional.of(allAccountsDTOMock));
        final List<AccountDetailsDTO> allAccountsDTOActual = accountService.getAll().orElse(null);

        Assert.assertTrue(new ReflectionEquals(allAccountsDTOActual).matches(allAccountsDTOMock));
    }

    @Test
    void testGetAllAccountsEmpty() {
        when(accountRepository.getAll())
                .thenReturn(Optional.empty());

        final Optional<List<AccountDetailsDTO>> all = accountService.getAll();
        assertThat(all.isEmpty());
    }


    @Test
    void testGetBalanceByAccountId() {
        final BalanceDTO balanceDTOMock = BalanceDTO.fromAccountToDTO(
                accountStub.getAccounts().get(1)
        );
        when(accountRepository.getBalanceByAccountId(anyInt())).thenReturn(Optional.of(balanceDTOMock));

        final BalanceDTO BalanceDTOActual = accountService.getBalanceByAccountId(anyInt()).orElse(null);

        Assert.assertTrue(new ReflectionEquals(balanceDTOMock).matches(BalanceDTOActual));
    }

    @Test
    void testGetBalanceByAccountIdEmpty() {
        when(accountRepository.getBalanceByAccountId(anyInt()))
                .thenReturn(Optional.empty());

        final Optional<BalanceDTO> balanceDTO = accountService.getBalanceByAccountId(anyInt());

        assertThat(balanceDTO.isEmpty());
    }

}
