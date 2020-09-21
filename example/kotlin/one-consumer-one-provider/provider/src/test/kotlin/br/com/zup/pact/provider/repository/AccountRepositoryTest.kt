package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.entity.AccountEntity
import br.com.zup.pact.provider.enum.AccountType
import br.com.zup.pact.provider.stub.AccountStub
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AccountRepositoryTest {
    private val accountStubMock: AccountStub = mockk()
    private val accountRepository: AccountRepository = AccountRepository(accountStubMock)

    @Test
    fun `Method getAll should return the AccountDetailsDTO objects returned by the stub`() {
        //  Given
        every { accountStubMock.getAllStubsDTOFormat() }.returns(mutableListOf(
                createAccountDetailsDTOList(), createAccountDetailsDTOList()
        ))

        //  When
        val actualReturn = accountRepository.getAll()

        //  Then
        Assertions.assertThat(actualReturn)
                .containsExactly(createAccountDetailsDTOList(), createAccountDetailsDTOList())
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
