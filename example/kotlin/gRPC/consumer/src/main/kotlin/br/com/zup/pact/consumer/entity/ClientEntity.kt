package br.com.zup.pact.consumer.entity

import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.dto.BalanceDTO

data class ClientEntity(
        val id: Int,
        val accountId: Int,
        val name: String,
        val finalName: String,
        val age: Int
) {
    fun toAccountDetailsDTO(): ClientDetailsDTO {
        return ClientDetailsDTO(
                id = id,
                accountId = accountId,
                name = name,
                finalName = finalName,
                age = age
        )
    }

//    fun toBalanceDTO(): BalanceDTO {
//        return BalanceDTO(
//                accountId = accountId,
//                clientId = clientId,
//                balance = balance
//        )
//    }
}