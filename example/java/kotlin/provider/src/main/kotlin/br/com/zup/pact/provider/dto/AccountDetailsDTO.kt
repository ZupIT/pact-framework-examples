package br.com.zup.pact.provider.dto

import br.com.zup.pact.provider.enums.AccountType
import br.com.zup.pact.provider.resource.AccountDetailsResponse
import br.com.zup.pact.provider.resource.AccountDetailsResponseOrBuilder

data class AccountDetailsDTO(
        val accountId: Int,
        val balance: Double,
        val accountType: AccountType
) {
//
//    fun toAccountDetailsResponse(): AccountDetailsResponse {
//        return AccountDetailsResponse.newBuilder()
//                .setAccountId(accountId.toString())
//                .setBalance(balance.toString())
//                .accountType(accountType.toString())
//                .build()
//    }
}

