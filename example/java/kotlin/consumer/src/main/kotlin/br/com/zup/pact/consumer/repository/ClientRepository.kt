package br.com.zup.pact.consumer.repository

import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.stub.ClientStub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ClientRepository(@Autowired val clientStub: ClientStub) {

    fun findByClientId(clientId: Int): Optional<ClientDetailsDTO> {
        return Optional.ofNullable(
                clientStub.clients[clientId]?.toAccountDetailsDTO()
        )
    }

    fun getAll(): List<ClientDetailsDTO> {
        return clientStub.getAllStubsDTOFormat()
    }

}