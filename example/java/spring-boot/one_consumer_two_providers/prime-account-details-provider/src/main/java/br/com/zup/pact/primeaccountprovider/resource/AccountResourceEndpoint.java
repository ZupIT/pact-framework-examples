package br.com.zup.pact.primeaccountprovider.resource;

import br.com.zup.pact.primeaccountprovider.dto.PrimeAccountDetailsDTO;
import br.com.zup.pact.primeaccountprovider.service.PrimeAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/v1/primeaccounts")
@Api("Prime Account details API")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountResourceEndpoint {

    private final PrimeAccountService primeAccountService;

    @GetMapping("/{clientId}")
    @ApiOperation(notes = "Return details of the ClientId,if the account is prime",
            value = "Get Account Details by client id",
            nickname = "getPrimeAccountDetailsByClientId",
            response = PrimeAccountDetailsDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Account Returned", response = PrimeAccountDetailsDTO.class),
            @ApiResponse(code = 404, message = "account Not Found"),
    })
    public ResponseEntity<PrimeAccountDetailsDTO> getPrimeAccountDetailsByClientId(@PathVariable("clientId") Integer clientId) {
        final Optional<PrimeAccountDetailsDTO> accountDetailsDTO = primeAccountService.getPrimeAccountDetailsByClientId(clientId);
        if (accountDetailsDTO.isPresent()) {
            final PrimeAccountDetailsDTO accountDetailsDTOFound = accountDetailsDTO.get();
            return new ResponseEntity<>(accountDetailsDTOFound, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
