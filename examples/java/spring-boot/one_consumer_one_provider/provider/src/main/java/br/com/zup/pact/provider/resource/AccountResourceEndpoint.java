package br.com.zup.pact.provider.resource;

import br.com.zup.pact.provider.dto.AccountDetailsDTO;
import br.com.zup.pact.provider.dto.BalanceDTO;
import br.com.zup.pact.provider.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
@Api("Account API")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountResourceEndpoint {

    private final AccountService accountService;

    @GetMapping("/{clientId}")
    @ApiOperation(notes = "Return details of and Account by ClientId",
            value = "Get Account Details by client id",
            nickname = "getAccountDetailsByClientId",
            response = AccountDetailsDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Account Returned", response = AccountDetailsDTO.class),
            @ApiResponse(code = 404, message = "account Not Found"),
    })
    public ResponseEntity<AccountDetailsDTO> getAccountDetailsByClientId(@PathVariable("clientId") Integer clientId) {
        final Optional<AccountDetailsDTO> accountDetailsDTO = accountService.getAccountDetailsByClientId(clientId);
        if (accountDetailsDTO.isPresent()) {
            final AccountDetailsDTO accountDetailsDTOFound = accountDetailsDTO.get();
            return new ResponseEntity<>(accountDetailsDTOFound, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @ApiOperation(notes = "Return all Accounts",
            value = "Get all Accounts Details",
            nickname = "getAll",
            response = AccountDetailsDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client Returned", response = AccountDetailsDTO.class),
            @ApiResponse(code = 404, message = "Client Not Found"),
    })
    public ResponseEntity<List<AccountDetailsDTO>> getAll() {
        final Optional<List<AccountDetailsDTO>> accountDetailsDTOS = accountService.getAll();
        if (accountDetailsDTOS.isPresent()) {
            final List<AccountDetailsDTO> accountDetailsDTOSFound = accountDetailsDTOS.get();
            return new ResponseEntity<>(accountDetailsDTOSFound, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/balance/{clientId}")
    @ApiOperation(notes = "Return balance account of clientId",
            value = "Get balance account of clientId",
            nickname = "getBalanceOfClientId",
            response = BalanceDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Balance Returned", response = BalanceDTO.class),
            @ApiResponse(code = 404, message = "Client Not Found"),
    })
    public ResponseEntity<BalanceDTO> getBalanceByClientId(@PathVariable("clientId") Integer clientId) {
        final Optional<BalanceDTO> balanceDTO = accountService.getBalanceByClientId(clientId);
        if (balanceDTO.isPresent()) {
            return new ResponseEntity<>(balanceDTO.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
