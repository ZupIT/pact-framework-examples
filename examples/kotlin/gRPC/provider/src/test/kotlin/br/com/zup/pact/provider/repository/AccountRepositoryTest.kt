package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.entity.AccountEntity
import br.com.zup.pact.provider.enums.AccountType
import br.com.zup.pact.provider.stub.AccountStub
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

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

        //Given
        every { accountStubMock.getAllStubsDTOFormat() }
                .returns(mutableListOf(
                        createAccountDetailsDTOList(), createAccountDetailsDTOList()
                ))

        // when
        val actualReturn = accountRepository.getAll()

        // then
        Assertions.assertThat(actualReturn)
                .containsExactly(createAccountDetailsDTOList(), createAccountDetailsDTOList())
    }

    @Test
    fun `Method findByAccountId should return the BalanceDTO list when stub return AccountEntity list`() {

        //given
        every { accountStubMock.accounts }
                .returns(
                        mutableMapOf<Int, AccountEntity>(
                                1 to createAccountEntity(1),
                                2 to createAccountEntity(2)
                        )
                )
        val expectedBalanceDTO = BalanceDTO(accountId = 1, balance = 100.0)

        //when
        val actualReturn = accountRepository.findByAccountId(1)

        // then
        Assertions.assertThat(actualReturn).isEqualTo(expectedBalanceDTO)
    }

    @Test
    fun `Method findByAccountId should return null when stub does not have the clientId `() {
        //given
        every { accountStubMock.accounts }
                .returns(
                        mutableMapOf<Int, AccountEntity>(
                                1 to createAccountEntity(1),
                                2 to createAccountEntity(2)
                        )
                )
        val expectedBalanceDTO = null

        //when
        val actualReturn = accountRepository.findByAccountId(3)

        // then
        Assertions.assertThat(actualReturn).isEqualTo(expectedBalanceDTO)
    }

    @Test
    fun `Method findByAccountId should return null when stub return is empty`() {
        //given
        every { accountStubMock.accounts }
                .returns(
                        mutableMapOf<Int, AccountEntity>()
                )
        val expectedBalanceDTO = null

        //when
        val actualReturn = accountRepository.findByAccountId(3)

        // then
        Assertions.assertThat(actualReturn).isEqualTo(expectedBalanceDTO)
    }

    private fun createAccountEntity(
            accountId: Int,
            clientId: Int = 100,
            balance: Double = 100.0,
            accountType: AccountType = AccountType.MASTER
    ) = AccountEntity(
            accountId = accountId,
            balance = balance,
            accountType = accountType
    )

    private fun createAccountDetailsDTOList(
            accountId: Int = 1,
            balance: Double = 10.0,
            accountType: AccountType = AccountType.MASTER
    ) = AccountDetailsDTO(accountId, balance, accountType)
}