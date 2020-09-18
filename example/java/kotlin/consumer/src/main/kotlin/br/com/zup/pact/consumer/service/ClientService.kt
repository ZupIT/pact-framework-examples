package br.com.zup.pact.consumer.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import java.util.*

interface ClientService {
    fun getClientDetailsByClientId(clientId: Int): Optional<ClientDetailsDTO>
    fun getAll(): List<ClientDetailsDTO>
    fun getBalanceByClientId(clientId: Int): Optional<BalanceDTO>
}
