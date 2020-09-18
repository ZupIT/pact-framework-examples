package br.com.zup.pact.provider.repository

import br.com.zup.pact.provider.stub.AccountStub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AccountRepository (@Autowired val accountStub: AccountStub) {

}