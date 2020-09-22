package br.com.zup.pact.provider.entity

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.enum.AccountType
import java.util.*

data class AccountEntity(
        val accountId: Int,
        val clientId: Int,
        val balance: Double,
        val accountType: AccountType
) {
//    fun fromEntityToDto(accountDetailsDTO: AccountDetailsDTO): AccountEntity {
//        if (Objects.nonNull(accountDetailsDTO)) {
//            return AccountEntity(accountType = accountDetailsDTO.accountType,
//                    balance = accountDetailsDTO.balance, accountId = accountId)
//
//        }
//    }
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