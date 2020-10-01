package br.com.zup.pact.consumer.repository

import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.entity.ClientEntity
import br.com.zup.pact.consumer.stub.ClientStub
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ClientRepositoryTest {

    private val clientStubMock: ClientStub = mockk()
    private val clientRepository: ClientRepository = ClientRepository(clientStubMock)

    @BeforeEach
    fun setUp() {
        clearMocks(clientStubMock)
    }

    @Test
    fun `Method findByClientId should return the ClientDetails by client id`() {

        every { clientStubMock.clients }
                .returns(
                        mutableMapOf(
                                1 to ClientEntity(1, 1, "any", "any", 25),
                                2 to ClientEntity(2, 2, "any", "any", 30)
                        )
                )
        val expectedClientDetailsDTO: ClientDetailsDTO? = ClientDetailsDTO(1, 1, "any", "any", 25)

        val actualReturn = clientRepository.findByClientId(1)

        Assertions.assertThat(actualReturn).isEqualTo(expectedClientDetailsDTO)
    }
}