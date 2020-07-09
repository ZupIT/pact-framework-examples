package br.com.zup.pact.accountapi.stub;

import br.com.zup.pact.accountapi.dto.AccountDetailsDTO;
import br.com.zup.pact.accountapi.entity.Account;
import br.com.zup.pact.accountapi.enums.AccountType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountStub {

    private static final Integer NUMBER_OF_STUBS = 10;
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("100");
    private static final List<AccountType> ACCOUNT_TYPES = Arrays.asList(AccountType.class.getEnumConstants());
    @Getter
    private Map<Integer, Account> accounts;


    public AccountStub() {
        log.info("\n\n\n\t\t\t\t\t\t ============================ Creating Account Stubs! ============================ \n");
        accounts = createStubs(NUMBER_OF_STUBS);
    }

    private Map<Integer, Account> createStubs(int numberOfStubs) {
        final Map<Integer, Account> accounts = new HashMap<>(NUMBER_OF_STUBS);
        for (int i = 1; i <= numberOfStubs; i++) {
            Collections.shuffle(ACCOUNT_TYPES);
            final Account account = Account.builder()
                    .id(i)
                    .clientId(i)
                    .balance(INITIAL_BALANCE)
                    .accountType(ACCOUNT_TYPES.get(0))
                    .build();
            accounts.put(i, account);
        }
        return accounts;
    }

    public List<AccountDetailsDTO> getAllStubsDTOFormat() {
        final List<AccountDetailsDTO> clientDetailsDTOS = new ArrayList<>();
        final List<Account> accounts = this.accounts.values().stream()
                .collect(Collectors.toList());
        for (Account account : accounts) {
            clientDetailsDTOS.add(Account.fromEntityToDto(account));
        }
        return clientDetailsDTOS;
    }
}
