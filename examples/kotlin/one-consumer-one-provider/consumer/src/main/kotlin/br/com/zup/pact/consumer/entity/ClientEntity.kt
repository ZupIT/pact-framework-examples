package br.com.zup.pact.consumer.entity

import br.com.zup.pact.consumer.dto.ClientDetailsDTO

data class ClientEntity (
        val clientId: Int,
        val accountId: Int,
        val firstName: String,
        val lastName: String,
        val age: Int
) {
    fun toAccountDetailsDTO(): ClientDetailsDTO {
        return ClientDetailsDTO(
                clientId = clientId,
                accountId = accountId,
                firstName = firstName,
                lastName = lastName,
                age = age
        )
    }
}
