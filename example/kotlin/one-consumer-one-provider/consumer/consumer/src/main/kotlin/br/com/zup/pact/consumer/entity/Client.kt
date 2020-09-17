package br.com.zup.pact.consumer.entity

import br.com.zup.pact.consumer.dto.ClientDetailsDTO

data class Client (
        private val id: Int,
        private val accountId: Int,
        private val name: String,
        private val finalName: String,
        private val age: Int )
{
    fun fromEntityToDto(): ClientDetailsDTO {
        return ClientDetailsDTO(
                id = id,
                accountId = accountId,
                age = age,
                name = name,
                finalName = finalName
        )
    }
}