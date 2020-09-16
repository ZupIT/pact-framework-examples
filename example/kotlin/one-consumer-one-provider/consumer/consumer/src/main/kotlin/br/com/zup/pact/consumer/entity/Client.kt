package br.com.zup.pact.consumer.entity

import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import java.util.*

class Client (
        private val id: Int,
        private val accountId: Int,
        private val name: String,
        private val finalName: String,
        private val age: Int )
{
    fun fromEntityToDto(client: Client): ClientDetailsDTO? {
        if (Objects.nonNull(client)) {
            return ClientDetailsDTO(
                    id = client.id,
                    accountId = client.accountId,
                    age = client.age,
                    name = client.name,
                    finalName = client.finalName
            )
        }
        return null
    }
}