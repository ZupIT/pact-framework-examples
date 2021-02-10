package br.com.zup.pact.provider.service

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import java.util.*

interface AccountService {
    fun getAll(): List<AccountDetailsDTO>
    fun findByAccountId(accountId: Int): BalanceDTO?
}
