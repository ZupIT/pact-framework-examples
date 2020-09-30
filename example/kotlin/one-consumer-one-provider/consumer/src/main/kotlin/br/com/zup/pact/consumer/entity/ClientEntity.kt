package br.com.zup.pact.consumer.entity

import br.com.zup.pact.consumer.dto.ClientDetailsDTO

class ClientEntity (
        val id: Int,
        val accountId: Int,
        val firstName: String,
        val lastName: String,
        val age: Int
) {
    fun toAccountDetailsDTO(): ClientDetailsDTO {
        return ClientDetailsDTO(
                id = id,
                accountId = accountId,
                firstName = firstName,
                lastName = lastName,
                age = age
        )
    }
}
