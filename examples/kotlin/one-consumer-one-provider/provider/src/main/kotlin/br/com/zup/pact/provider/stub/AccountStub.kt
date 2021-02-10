package br.com.zup.pact.provider.stub

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.entity.AccountEntity
import br.com.zup.pact.provider.enum.AccountType
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class AccountStub {
    private val numberOfStubs = 10
    private val initialBalance = 100.0
    private val initialAccountType = AccountType.COMMON

    val accounts = mutableMapOf<Int, AccountEntity>()

    @PostConstruct
    private fun createStubs() {
        accounts.putAll(
                (1..numberOfStubs).map { AccountEntity(it, it, initialBalance, initialAccountType) }
                        .associateBy({ it.accountId }, { it })
        )
    }

    fun getAllStubsAccountDTOFormat(): List<AccountDetailsDTO> {
        return accounts.values.map(AccountEntity::toAccountDetailsDTO)
    }

    fun getAllStubsBalanceDTOFormat(): List<BalanceDTO> {
        return accounts.values.map(AccountEntity::toBalanceDTO)
    }
}