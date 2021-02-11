package br.com.zup.pact.consumer.repository

import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.stub.ClientStub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ClientRepository (@Autowired val clientStub: ClientStub) {
    fun findByClientId(clientId: Int): ClientDetailsDTO? {
        return clientStub.clients[clientId]?.toAccountDetailsDTO()
    }

    fun getAll(): List<ClientDetailsDTO>? {
        return clientStub.getAllStubsDTOFormat()
    }
}
