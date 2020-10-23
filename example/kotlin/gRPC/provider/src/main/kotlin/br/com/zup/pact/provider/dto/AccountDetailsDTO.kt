package br.com.zup.pact.provider.dto

import br.com.zup.pact.provider.enums.AccountType

data class AccountDetailsDTO(
        val accountId: Int,
        val balance: Double,
        val accountType: AccountType
) {
}

