package br.com.zup.pact.consumer.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import java.util.*

interface ClientService {
    fun getClientDetails(clientId: Int): ClientDetailsDTO?
    fun getAll(): List<ClientDetailsDTO>
    fun getBalance(clientId: Int): BalanceDTO?
}
