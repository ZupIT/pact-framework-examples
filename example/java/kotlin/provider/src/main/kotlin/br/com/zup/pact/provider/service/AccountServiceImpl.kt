package br.com.zup.pact.provider.service

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountServiceImpl(@Autowired val accountRepository: AccountRepository) : AccountService {

    override fun getAll(): List<AccountDetailsDTO> {
        return accountRepository.getAll()
    }

    override fun findByAccountId(accountId: Int): Optional<BalanceDTO> {
        return accountRepository.findByAccountId(accountId)
    }
}