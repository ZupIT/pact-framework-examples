package br.com.zup.pact.provider.entity

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.enums.AccountType

data class AccountEntity(
        val accountId: Int,
        val clientId: Int,
        val balance: Double,
        val accountType: AccountType
) {
    fun toAccountDetailsDTO(): AccountDetailsDTO {
        return AccountDetailsDTO(
                accountId = accountId,
                balance = balance,
                accountType = accountType
        )
    }

    fun toBalanceDTO(): BalanceDTO {
        return BalanceDTO(
                accountId = accountId,
                clientId = clientId,
                balance = balance
        )
    }
}