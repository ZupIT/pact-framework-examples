package br.com.vinirib.provider.pact.account.stub;

import br.com.vinirib.provider.pact.account.dto.AccountDetailsDTO;
import br.com.vinirib.provider.pact.account.entity.Account;
import br.com.vinirib.provider.pact.account.enums.AccountType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountStub {

    private static final Integer NUMBER_OF_STUBS = 10;
    private static final List<AccountType> ACCOUNT_TYPES = Arrays.asList(AccountType.class.getEnumConstants());
    @Getter
    private final Map<Integer, Account> accounts;
    private final double MAX_BALANCE = 29999.00;
    private final double MIN_BALANCE = -100.00;


    public AccountStub() {
        log.info("\n\n\n\t\t\t\t\t\t ============================ Creating Account Stubs! ============================ \n");
        accounts = createStubs(NUMBER_OF_STUBS);
    }

    private Map<Integer, Account> createStubs(int numberOfStubs) {
        Map<Integer, Account> accounts = new HashMap<>(NUMBER_OF_STUBS);
        for (int i = 1; i <= numberOfStubs; i++) {
            Collections.shuffle(ACCOUNT_TYPES);
            final Account account = Account.builder()
                    .id(i)
                    .clientId(i)
                    .balance(new BigDecimal(getRandomAmount()))
                    .accountType(ACCOUNT_TYPES.get(0))
                    .build();
            accounts.put(i, account);
        }
        return accounts;
    }

    private double getRandomAmount() {
        return Math.random() * (MAX_BALANCE - MIN_BALANCE) + MIN_BALANCE;
    }

    public List<AccountDetailsDTO> getAllStubsDTOFormat() {
        List<AccountDetailsDTO> clientDetailsDTOS = new ArrayList<>();
        final List<Account> accounts = this.accounts.values().stream()
                .collect(Collectors.toList());
        for (Account account : accounts) {
            clientDetailsDTOS.add(Account.fromEntityToDto(account));
        }
        return clientDetailsDTOS;
    }
}
