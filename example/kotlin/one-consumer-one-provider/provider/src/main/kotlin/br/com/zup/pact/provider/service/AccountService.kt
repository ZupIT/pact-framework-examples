package br.com.zup.pact.provider.service

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import java.util.*

interface AccountService {
    fun getAccountDetailsByClientId(clientId: Int): AccountDetailsDTO?
    fun getAll(): List<AccountDetailsDTO>
    fun getBalanceByClientId(clientId: Int): BalanceDTO?
}
