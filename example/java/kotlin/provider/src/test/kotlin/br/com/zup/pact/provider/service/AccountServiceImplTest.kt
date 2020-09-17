package br.com.zup.pact.provider.service

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.enums.AccountType
import br.com.zup.pact.provider.repository.AccountRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class AccountServiceImplTest {

    private val accountRepositoryMock: AccountRepository = mockk()
    private val accountService: AccountService = AccountServiceImpl(accountRepositoryMock)

    @BeforeEach
    fun setUp() {
        clearMocks(accountRepositoryMock)
    }

    @Test
    fun `Method getAccountDetailsByClientId should return findByClientId from the repository`() {

        every { accountRepositoryMock.findByClientId(1) }
                .returns(Optional.of(AccountDetailsDTO(
                        accountId = 1,
                        balance = 100.0,
                        accountType = AccountType.MASTER
                )))

        val expectedAccountDetailsDTO = accountService.getAccountDetailsByClientId(1)

        Assertions.assertThat(expectedAccountDetailsDTO).isEqualTo(
                Optional.of(AccountDetailsDTO(
                        accountId = 1,
                        balance = 100.0,
                        accountType = AccountType.MASTER
                ))
        )
    }

    @Test
    fun `Method getAll should return getAll from the repository`() {

        every { accountRepositoryMock.getAll() }
                .returns(listOf(AccountDetailsDTO(
                        accountId = 1,
                        balance = 100.0,
                        accountType = AccountType.MASTER
                )))

        val expectedAccountDetailsDTO = accountService.getAll()

        Assertions.assertThat(expectedAccountDetailsDTO).isEqualTo(
                listOf(AccountDetailsDTO(
                        accountId = 1,
                        balance = 100.0,
                        accountType = AccountType.MASTER
                ))
        )
    }

    @Test
    fun `Method getBalanceByClientId should return getBalanceByClientId from the repository`() {

        every { accountRepositoryMock.getBalanceByClientId(1) }
                .returns(Optional.of(BalanceDTO(
                        accountId = 1,
                        clientId = 1,
                        balance = 100.0
                )))

        val expectedBalanceDTO = accountService.getBalanceByClientId(1)

        Assertions.assertThat(expectedBalanceDTO).isEqualTo(
                Optional.of(BalanceDTO(
                        accountId = 1,
                        clientId = 1,
                        balance = 100.0
                ))
        )
    }
}