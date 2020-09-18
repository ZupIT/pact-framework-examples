package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.stub.AccountStub
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AccountRepositoryTest {
    private val accountStubMock: AccountStub = mockk()
    private val accountRepository: AccountRepository = AccountRepository(accountStubMock)

    @Test
    fun `Method getAll should return the AccountDetailsDTO objects returned by the stub`() {

    }
}
