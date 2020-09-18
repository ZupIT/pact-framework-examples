package br.com.zup.pact.provider.dto

import br.com.zup.pact.provider.enum.AccountType

data class AccountDetailsDTO (
        val accountId: Int,
        val balance: Double,
        val accountType: AccountType
)
