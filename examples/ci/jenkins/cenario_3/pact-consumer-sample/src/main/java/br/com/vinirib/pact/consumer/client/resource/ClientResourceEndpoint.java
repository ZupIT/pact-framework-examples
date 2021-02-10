package br.com.vinirib.pact.consumer.client.resource;

import br.com.vinirib.pact.consumer.client.dto.BalanceDTO;
import br.com.vinirib.pact.consumer.client.dto.ClientDetailsDTO;
import br.com.vinirib.pact.consumer.client.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1/clients")
@Api(value = "Clients API", tags = {"Clients API"})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientResourceEndpoint {

    private final ClientService clientService;

    @GetMapping("/{clientId}")
    @ApiOperation(notes = "Return details of and Client",
            value = "Get Client Details",
            nickname = "getClientDetails",
            response = ClientDetailsDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client Returned", response = ClientDetailsDTO.class),
            @ApiResponse(code = 404, message = "Client Not Found"),
    })
    public ResponseEntity<ClientDetailsDTO> getClientDetails(@PathVariable("clientId") Integer clientId) {
        final Optional<ClientDetailsDTO> ClientDetailsDTO = clientService.getClientDetails(clientId);
        if (ClientDetailsDTO.isPresent()) {
            final ClientDetailsDTO clientDetailsDTO = ClientDetailsDTO.get();
            return new ResponseEntity<>(clientDetailsDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @ApiOperation(notes = "Return all Clients",
            value = "Get all Client Details",
            nickname = "getClientDetails",
            response = ClientDetailsDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client Returned", response = ClientDetailsDTO.class),
            @ApiResponse(code = 404, message = "Client Not Found"),
    })
    public ResponseEntity<List<ClientDetailsDTO>> getAll() {
        final Optional<List<ClientDetailsDTO>> ClientDetailsDTO = clientService.getAll();
        if (ClientDetailsDTO.isPresent()) {
            final List<ClientDetailsDTO> clientDetailsDTOS = ClientDetailsDTO.get();
            return new ResponseEntity<>(clientDetailsDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/balance/{clientId}")
    @ApiOperation(notes = "Return balance of an client",
            value = "Get balance of a client",
            nickname = "getBalance",
            response = BalanceDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client Returned", response = BalanceDTO.class),
            @ApiResponse(code = 404, message = "Client Not Found"),
    })
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable("clientId") Integer clientId) {
        final Optional<BalanceDTO> balanceDTO = clientService.getBalance(clientId);
        if (Objects.nonNull(balanceDTO)) {
            final BalanceDTO balanceDTOFound = balanceDTO.get();
            return new ResponseEntity<>(balanceDTOFound, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
