package br.com.zup.pact.provider.stub

import br.com.zup.pact.provider.entity.AccountEntity
import br.com.zup.pact.provider.enum.AccountType
import org.springframework.stereotype.Component

@Component
class AccountStub {
    private val numberOfStubs = 10
    private val initialBalance = 100.0
    private val initialAccountType = AccountType.COMMON

    val accounts = mutableMapOf<Int, AccountEntity>()

}