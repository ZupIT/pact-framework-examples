package br.com.zup.pact.provider.stub

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.entity.AccountEntity
import br.com.zup.pact.provider.enums.AccountType
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
                (1..numberOfStubs)
                        .map { item -> AccountEntity(item, item, initialBalance, initialAccountType) }
                        .associateBy({ it.accountId }, { it })
        )
    }

    fun getAllStubsDTOFormat(): List<AccountDetailsDTO> {
       return accounts.values.map(AccountEntity::toDto)
    }

}