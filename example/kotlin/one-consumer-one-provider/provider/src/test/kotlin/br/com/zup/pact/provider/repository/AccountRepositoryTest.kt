package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
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

        every { accountStubMock.getAllStubsAccountDTOFormat() }.returns(mutableListOf(
                createAccountDetailsDTOList(), createAccountDetailsDTOList()
        ))

        val actualReturn = accountRepository.getAllAccounts()

        Assertions.assertThat(actualReturn)
                .containsExactly(createAccountDetailsDTOList(), createAccountDetailsDTOList())
    }

    //  TODO -> Criar teste para o findByClientId

    @Test
    fun `Method getBalanceByClientId should return the BalanceDTO list when stub return AccountEntity list`() {

        every { accountStubMock.getAllStubsBalanceDTOFormat() }
                .returns(
                        mutableListOf(createBalanceDetailsDTOList(), createBalanceDetailsDTOList())
                )
        val expectBalanceDTO: BalanceDTO? = BalanceDTO(1, 1, 100.0)

        val actualReturn = accountRepository.getBalanceByClientId(1)

        Assertions.assertThat(actualReturn).isEqualTo(expectBalanceDTO)
    }

    @Test
    fun `Method getBalanceByClientId should return null when stub does not have the ClientId`() {

        every { accountStubMock.getAllStubsBalanceDTOFormat() }
                .returns(
                        mutableListOf(createBalanceDetailsDTOList())
                )

        val actualReturn = accountRepository.getBalanceByClientId(2)

        Assertions.assertThat(actualReturn).isNull()
    }

    //  TODO -> verificar se este teste não se tornou redundante:
    @Test
    fun `Method getBalanceByClientId should return null when stub returns is null`() {

        every { accountStubMock.getAllStubsBalanceDTOFormat() }
                .returns(
                        mutableListOf()
                )

        val actualReturn = accountRepository.getBalanceByClientId(3)
        println(actualReturn)
        Assertions.assertThat(actualReturn).isNull()
    }

    private fun createAccountDetailsDTOList(
            accountId: Int = 1,
            balance: Double = 10.0,
            accountType: AccountType = AccountType.MASTER
    ) = AccountDetailsDTO(accountId, balance, accountType)

    private fun createBalanceDetailsDTOList(
            accountId: Int = 1,
            clientId: Int = 1,
            balance: Double = 100.0
    ) = BalanceDTO(accountId, clientId, balance)
}
