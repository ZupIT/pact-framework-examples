package br.com.zup.pact.consumer.dto

data class ClientDetailsDTO(
        val id: Int,
        val accountId: Int,
        val name: String,
        val finalName: String,
        val age: Int
)

