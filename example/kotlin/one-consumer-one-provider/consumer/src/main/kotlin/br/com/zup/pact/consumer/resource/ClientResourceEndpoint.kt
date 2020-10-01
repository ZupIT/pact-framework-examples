package br.com.zup.pact.consumer.resource

import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.service.ClientService
import br.com.zup.pact.consumer.service.ClientServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
}
