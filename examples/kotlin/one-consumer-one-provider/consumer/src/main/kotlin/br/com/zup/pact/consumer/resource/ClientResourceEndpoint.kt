package br.com.zup.pact.consumer.resource

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.service.ClientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/clients")
class ClientResourceEndpoint(val clientService: ClientService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<ClientDetailsDTO>> {
        val allClientDetailsDTO: List<ClientDetailsDTO>? = clientService.getAll()
        return if (allClientDetailsDTO.isNullOrEmpty()) ResponseEntity(HttpStatus.NOT_FOUND)
        else ResponseEntity(allClientDetailsDTO, HttpStatus.OK)
    }

    @GetMapping("/{client-id}")
    fun getClientDetails(@PathVariable("client-id") clientId: Int): ResponseEntity<ClientDetailsDTO> {
        return clientService.getClientDetails(clientId)?.let {
            ResponseEntity(it, HttpStatus.OK)
        }?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/{client-id}/balance")
    fun getBalance(@PathVariable("client-id") clientId: Int): ResponseEntity<BalanceDTO> {
        return clientService.getBalance(clientId)?.let {
            ResponseEntity(it, HttpStatus.OK)
        }?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
