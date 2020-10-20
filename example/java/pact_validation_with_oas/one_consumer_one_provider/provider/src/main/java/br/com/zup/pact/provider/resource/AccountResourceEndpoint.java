package br.com.zup.pact.provider.resource;

import br.com.zup.pact.provider.dto.AccountDetailsDTO;
import br.com.zup.pact.provider.dto.BalanceDTO;
import br.com.zup.pact.provider.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/accounts")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountResourceEndpoint {

    private final AccountService accountService;

    @GetMapping("/{clientId}")
    public ResponseEntity<AccountDetailsDTO> getAccountDetailsByClientId(@PathVariable("clientId") Integer clientId) {
        final Optional<AccountDetailsDTO> accountDetailsDTO = accountService.getAccountDetailsByClientId(clientId);
        if (accountDetailsDTO.isPresent()) {
            final AccountDetailsDTO accountDetailsDTOFound = accountDetailsDTO.get();
            return new ResponseEntity<>(accountDetailsDTOFound, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<AccountDetailsDTO>> getAll() {
        final Optional<List<AccountDetailsDTO>> accountDetailsDTOS = accountService.getAll();
        if (accountDetailsDTOS.isPresent()) {
            final List<AccountDetailsDTO> accountDetailsDTOSFound = accountDetailsDTOS.get();
            return new ResponseEntity<>(accountDetailsDTOSFound, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/{accountId}/balance/")
    @Operation(summary = "Get balance by accountId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the balance for the account",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BalanceDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Account id not found",
                    content = @Content) })
    public ResponseEntity<BalanceDTO> getBalanceByClientId(@PathVariable("accountId") Integer clientId) {
        final Optional<BalanceDTO> balanceDTO = accountService.getBalanceByClientId(clientId);
        if (balanceDTO.isPresent()) {
            return new ResponseEntity<>(balanceDTO.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
