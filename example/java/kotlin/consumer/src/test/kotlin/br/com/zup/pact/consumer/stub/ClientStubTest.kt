package br.com.zup.pact.consumer.stub

import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.entity.ClientEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ClientStubTest() {

    @Autowired
    private lateinit var clientStub: ClientStub

    @Test
    fun `New AccountStub class should has 10 ClientEntity`() {
        val numberOfStubs = 10
        val minAge = 18
        val maxAge = 70

        val actualResult: Map<Int, ClientEntity> = clientStub.clients

        Assertions.assertThat(actualResult).hasSize(numberOfStubs)

        (1..numberOfStubs).forEach { id ->

            val actualEntity = actualResult[id]

            Assertions.assertThat(ClientEntity(id, id, "", "", 18))
                    .usingRecursiveComparison()
                    .ignoringFields("name", "finalName", "age")
                    .isEqualTo(actualEntity)
            Assertions.assertThat(actualEntity?.name).isNotBlank()
            Assertions.assertThat(actualEntity?.finalName).isNotBlank()
            Assertions.assertThat(actualEntity?.age).isBetween(minAge, maxAge)
        }
    }

    @Test
    fun `Method getAllStubsDTOFormat should return the ClientEntity list`() {
        val numberOfStubs = 10
        val minAge = 18
        val maxAge = 70


        val actualResult: List<ClientDetailsDTO> = clientStub.getAllStubsDTOFormat()

        Assertions.assertThat(actualResult).hasSize(numberOfStubs)

        (0 until numberOfStubs).forEach { index ->

            val actualEntity = actualResult[index]

            Assertions.assertThat(ClientDetailsDTO(index + 1, index + 1, "", "", 18))
                    .usingRecursiveComparison()
                    .ignoringFields("name", "finalName", "age")
                    .isEqualTo(actualEntity)
            Assertions.assertThat(actualEntity.name).isNotBlank()
            Assertions.assertThat(actualEntity.finalName).isNotBlank()
            Assertions.assertThat(actualEntity.age).isBetween(minAge, maxAge)
        }
    }
}