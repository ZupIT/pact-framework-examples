package br.com.zup.pact.provider.dto

import br.com.zup.pact.provider.enum.AccountType

data class AccountDetailsDTO (
        val accountId: Int,
        val balance: Double,
        val accountType: AccountType
) {
//    fun fromDtoToEntity(accountDetailsDTO: AccountDetailsDTO): AccountEntity? {
//        if (Objects.nonNull(accountDetailsDTO)) {
//            return AccountEntity(accountType = accountDetailsDTO.accountType,
//                    balance = accountDetailsDTO.balance)
//        }
//        return null;
//    }
}
