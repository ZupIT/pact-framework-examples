package br.com.zup.pact.provider.service

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountServiceImpl(@Autowired val accountRepository: AccountRepository): AccountService {
    override fun getAccountDetailsByClientId(clientId: Int): Optional<AccountDetailsDTO> {
        return accountRepository.findByClientId(clientId)
    }

    override fun getAll(): List<AccountDetailsDTO> {
        return accountRepository.getAll()
    }

    override fun getBalanceByClientId(clientId: Int): Optional<BalanceDTO> {
        TODO("Not yet implemented")
    }

}