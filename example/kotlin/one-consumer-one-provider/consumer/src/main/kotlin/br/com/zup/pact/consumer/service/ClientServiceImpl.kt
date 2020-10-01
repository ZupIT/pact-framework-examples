package br.com.zup.pact.consumer.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired

class ClientServiceImpl (
        @Autowired val clientRepository: ClientRepository
): ClientService {
    override fun getClientDetails(clientId: Int): ClientDetailsDTO? {
        return clientRepository.findByClientId(clientId)
    }

    override fun getAll(): List<ClientDetailsDTO>? {
        return clientRepository.getAll()
    }

    override fun getBalance(clientId: Int): BalanceDTO? {
        TODO("Not yet implemented")
    }
}