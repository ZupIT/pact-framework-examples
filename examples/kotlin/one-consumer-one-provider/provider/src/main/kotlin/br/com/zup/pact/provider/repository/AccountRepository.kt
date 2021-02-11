package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.stub.AccountStub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AccountRepository (@Autowired val accountStub: AccountStub) {

    fun getAccountByClientId(clientId: Int): AccountDetailsDTO? {
        return getAllAccounts().firstOrNull { entity -> entity.accountId == clientId }
    }

    fun getAllAccounts(): List<AccountDetailsDTO> {
        return accountStub.getAllStubsAccountDTOFormat()
    }

    fun getAllAccountsBalance(): List<BalanceDTO> {
        return accountStub.getAllStubsBalanceDTOFormat()
    }

    fun getBalanceByClientId(clientId: Int): BalanceDTO? {
        return getAllAccountsBalance().firstOrNull { entity -> entity.clientId == clientId }
    }
}