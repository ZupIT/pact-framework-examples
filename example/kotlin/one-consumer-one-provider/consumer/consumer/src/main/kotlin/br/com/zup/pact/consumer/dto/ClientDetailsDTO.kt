package br.com.zup.pact.consumer.dto

import br.com.zup.pact.consumer.entity.Client
import java.util.*

class ClientDetailsDTO (
        private val id: Int,
        private val accountId: Int,
        private val name: String,
        private val finalName: String,
        private val age: Int ) {

    fun fromDtoToEntity(clientDetailsDTO: ClientDetailsDTO): Client? {
        if (Objects.nonNull(clientDetailsDTO)) {
            return Client(
                    id = clientDetailsDTO.id,
                    accountId = clientDetailsDTO.accountId,
                    name = clientDetailsDTO.name,
                    finalName = clientDetailsDTO.finalName,
                    age = clientDetailsDTO.age
            )
        }
        return null
    }
}