package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.entity.AccountEntity
import br.com.zup.pact.provider.stub.AccountStub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountRepository (@Autowired val accountStub: AccountStub) {

    fun findByClientId(clientId: Int): Optional<AccountDetailsDTO> {
        return Optional.ofNullable(
                accountStub.accounts.values
                        .filter { entity -> entity.accountId == clientId }
                        .map(AccountEntity::toAccountDetailsDTO)
                        .getOrNull(0)
        )
    }

    fun getAll(): List<AccountDetailsDTO> {
        return accountStub.getAllStubsDTOFormat()
    }

    fun getBalanceByClientId(clientId: Int): Optional<BalanceDTO> {
        return Optional.ofNullable(
                accountStub.accounts.values.filter { entity -> entity.accountId == clientId }
                        .map(AccountEntity::toBalanceDTO)
                        .getOrNull(index = 0)
        )
    }
}