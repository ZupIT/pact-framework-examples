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
import java.util.*

@ExtendWith(MockKExtension::class)
class ClientRepositoryTest {

    private val clientStubMock: ClientStub = mockk()
    private val clientRepository: ClientRepository = ClientRepository(clientStubMock)

    @BeforeEach
    fun setUp() {
        clearMocks(clientStubMock)
    }

    @Test
    fun `Method findByClientId should return the ClientDetailsDTO list when stub return ClientEntity list`() {

        //given
        every { clientStubMock.clients }
                .returns(
                        mutableMapOf<Int, ClientEntity>(
                                1 to createClientEntity(1),
                                2 to createClientEntity(2)
                        )
                )
        val expectedClientDetailsDTO = Optional.of(createClientDetailsDTO(accountId = 1))

        //when
        val actualReturn = clientRepository.findByClientId(1)

        // then
        Assertions.assertThat(actualReturn).isEqualTo(expectedClientDetailsDTO)
    }

    @Test
    fun `Method findByClientId should return Optional empty when stub does not have the clientId `() {
        //given
        every { clientStubMock.clients }
                .returns(
                        mutableMapOf<Int, ClientEntity>(
                                1 to createClientEntity(1),
                                2 to createClientEntity(2)
                        )
                )
        val expectedClientDetailsDTO = Optional.empty<ClientDetailsDTO>()

        //when
        val actualReturn = clientRepository.findByClientId(3)

        // then
        Assertions.assertThat(actualReturn).isEqualTo(expectedClientDetailsDTO)
    }

    @Test
    fun `Method findByClientId should return Optional empty when stub return is empty`() {
        //given
        every { clientStubMock.clients }.returns(mutableMapOf())
        val expectedClientDetailsDTO = Optional.empty<ClientDetailsDTO>()

        //when
        val actualReturn = clientRepository.findByClientId(3)

        // then
        Assertions.assertThat(actualReturn).isEqualTo(expectedClientDetailsDTO)
    }

    @Test
    fun `Method getAll should return the ClientDetailsDTO objects returned by the stub`() {

        //Given
        every { clientStubMock.getAllStubsDTOFormat() }
                .returns(mutableListOf(
                        createClientDetailsDTO(1), createClientDetailsDTO(2)
                ))

        // when
        val actualReturn = clientRepository.getAll()

        // then
        Assertions.assertThat(actualReturn)
                .containsExactly(createClientDetailsDTO(1), createClientDetailsDTO(2))
    }

    private fun createClientEntity(
            accountId: Int,
            id: Int = 100,
            name: String = "First name",
            finalName: String = "Final name",
            age: Int = 18
    ) = ClientEntity(
            accountId = accountId,
            id = id,
            name = name,
            finalName = finalName,
            age = age
    )

    private fun createClientDetailsDTO(
            accountId: Int,
            id: Int = 100,
            name: String = "First name",
            finalName: String = "Final name",
            age: Int = 18
    ) = ClientDetailsDTO(
            id,
            accountId,
            name,
            finalName,
            age
    )
}