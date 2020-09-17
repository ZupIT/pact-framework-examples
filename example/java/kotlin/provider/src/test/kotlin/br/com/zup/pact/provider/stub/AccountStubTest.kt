package br.com.zup.pact.provider.stub

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.entity.AccountEntity
import br.com.zup.pact.provider.enums.AccountType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountStubTest() {

    @Autowired
    private lateinit var accountStub: AccountStub

    @Test
    fun `New AccountStub class should has 10 AccountEntity`() {
        val numberOfStubs = 10
        val initialBalance = 100.0
        val initialAccountType = AccountType.COMMON

        val result: Map<Int, AccountEntity> = accountStub.accounts

        Assertions.assertThat(result).hasSize(numberOfStubs)

        (1..numberOfStubs).forEach { id ->
            Assertions.assertThat(AccountEntity(id, id, initialBalance, initialAccountType))
                    .isEqualTo(result[id])
        }
    }

    @Test
    fun `Method getAllStubsDTOFormat should return the AccountDetailsDTO list`() {
        val numberOfStubs = 10
        val initialBalance = 100.0
        val initialAccountType = AccountType.COMMON

        val result: List<AccountDetailsDTO> = accountStub.getAllStubsDTOFormat()

        Assertions.assertThat(result).hasSize(numberOfStubs)

        (0 until numberOfStubs).forEach { index ->
            Assertions.assertThat(AccountDetailsDTO(index + 1, initialBalance, initialAccountType))
                    .isEqualTo(result[index])
        }
    }
}