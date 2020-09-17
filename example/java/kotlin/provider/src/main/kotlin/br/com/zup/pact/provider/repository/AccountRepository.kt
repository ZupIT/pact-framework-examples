package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.stub.AccountStub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountRepository(@Autowired val accountStub: AccountStub) {

//    fun getAll() : List<AccountDetailsDTO> {
//        return this.accountStub.accountDetailsDTOList
//    }


}