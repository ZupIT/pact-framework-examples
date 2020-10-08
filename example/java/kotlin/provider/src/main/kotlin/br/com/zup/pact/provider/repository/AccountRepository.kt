package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.entity.AccountEntity
import br.com.zup.pact.provider.stub.AccountStub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AccountRepository(@Autowired val accountStub: AccountStub) {

    fun getAll(): List<AccountDetailsDTO> {
        return accountStub.getAllStubsDTOFormat()
    }

    fun findByAccountId(accountId: Int): BalanceDTO? {

        return accountStub.accounts.values
                .filter { entity -> entity.accountId == accountId }
                .map(AccountEntity::toBalanceDTO)
                .getOrNull(0)

    }

}