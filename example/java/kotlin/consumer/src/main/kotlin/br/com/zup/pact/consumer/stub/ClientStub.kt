package br.com.zup.pact.consumer.stub

import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.entity.ClientEntity
import com.github.javafaker.Faker
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class ClientStub {

    private val numberOfStubs = 10
    private val minAge = 18
    private val maxAge = 70

    val clients = mutableMapOf<Int, ClientEntity>()

    @PostConstruct
    private fun createStubs() {
        val faker = Faker()

        clients.putAll(
                (1..numberOfStubs)
                        .map {
                            ClientEntity(
                                    id = it,
                                    accountId = it,
                                    age = (minAge until maxAge).random(),
                                    name = faker.name().firstName(),
                                    finalName = faker.name().lastName()
                            )
                        }
                        .associateBy({ it.accountId }, { it })
        )
    }

    fun getAllStubsDTOFormat(): List<ClientDetailsDTO> {
        return clients.values.map(ClientEntity::toAccountDetailsDTO)
    }

}