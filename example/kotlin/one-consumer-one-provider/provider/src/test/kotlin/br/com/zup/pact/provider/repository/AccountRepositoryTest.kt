package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.entity.AccountEntity
import br.com.zup.pact.provider.enum.AccountType
import br.com.zup.pact.provider.stub.AccountStub
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
class AccountRepositoryTest {
    private val accountStubMock: AccountStub = mockk()
    private val accountRepository: AccountRepository = AccountRepository(accountStubMock)

    @BeforeEach
    fun setUp() {
        clearMocks(accountStubMock)
    }

    @Test
    fun `Method getAll should return the AccountDetailsDTO objects returned by the stub`() {

        every { accountStubMock.getAllStubsDTOFormat() }.returns(mutableListOf(
                createAccountDetailsDTOList(), createAccountDetailsDTOList()
        ))

        val actualReturn = accountRepository.getAll()

        Assertions.assertThat(actualReturn)
                .containsExactly(createAccountDetailsDTOList(), createAccountDetailsDTOList())
    }

    @Test
    fun `Method getBalanceByClientId should return the BalanceDTO list when stub return AccountEntity list`() {

        every { accountStubMock.accounts }
                .returns(
                        mutableMapOf(
                                1 to createAccountEntity(1),
                                2 to createAccountEntity(2)
                        )
                )
        val expectBalanceDTO = Optional.of(BalanceDTO(1, 100, 100.0))

        val actualReturn = accountRepository.getBalanceByClientId(1)

        Assertions.assertThat(actualReturn).isEqualTo(expectBalanceDTO)
    }

    @Test
    fun `Method getBalanceByClientId should return Optional empty when stub does not have the ClientId`() {

        every { accountStubMock.accounts }
                .returns(
                        mutableMapOf<Int, AccountEntity>(
                                1 to createAccountEntity(1),
                                2 to createAccountEntity(2)
                        )
                )
        val expectedBalanceDTO = Optional.empty<BalanceDTO>()

        val actualReturn = accountRepository.getBalanceByClientId(3)

        Assertions.assertThat(actualReturn).isEqualTo(expectedBalanceDTO)
    }

    @Test
    fun `Method getBalanceByClientId should return Optional empty when stub returns is empty`() {

        every { accountStubMock.accounts }
                .returns(
                        mutableMapOf<Int, AccountEntity>()
                )
        val expectedBalanceDTO = Optional.empty<BalanceDTO>()

        val actualReturn = accountRepository.getBalanceByClientId(3)

        Assertions.assertThat(actualReturn).isEqualTo(expectedBalanceDTO)
    }

    private fun createAccountEntity(
            accountId: Int,
            clientId: Int = 100,
            balance: Double = 100.0,
            accountType: AccountType = AccountType.MASTER
    ) = AccountEntity(
            accountId = accountId,
            clientId = clientId,
            balance = balance,
            accountType = accountType
    )

    private fun createAccountDetailsDTOList(
            accountId: Int = 1,
            balance: Double = 10.0,
            accountType: AccountType = AccountType.MASTER
    ) = AccountDetailsDTO(accountId, balance, accountType)
}
