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

    @Test
    fun `Method findByClientId must return null if clientId does not exist in the list`() {

        every { clientStubMock.clients }
                .returns(
                        mutableMapOf(
                                1 to ClientEntity(1, 1, "any", "any", 25),
                                2 to ClientEntity(2, 2, "any", "any", 30)
                        )
                )
        val expectedClientDetailsDTO: ClientDetailsDTO? = null

        val actualReturn = clientRepository.findByClientId(3)

        Assertions.assertThat(actualReturn).isEqualTo(expectedClientDetailsDTO)
    }

    @Test
    fun `Method getAll must return a list of ClientDetailsDTO`() {

        every { clientStubMock.getAllStubsDTOFormat() }
                .returns(
                        mutableListOf(
                                ClientDetailsDTO(1, 1, "any", "any", 25),
                                ClientDetailsDTO(2, 2, "any", "any", 40),
                                ClientDetailsDTO(3, 3, "any", "any", 45)
                        )
                )

        val actualReturn = clientRepository.getAll()

        Assertions.assertThat(actualReturn).hasSize(3)
    }

    @Test
    fun `Method getAll should return empty list if the ClientDetails list is empty`() {

        every { clientStubMock.getAllStubsDTOFormat() }
                .returns(
                        mutableListOf()
                )

        val actualReturn = clientRepository.getAll()

        Assertions.assertThat(actualReturn).isEmpty()
    }
}
