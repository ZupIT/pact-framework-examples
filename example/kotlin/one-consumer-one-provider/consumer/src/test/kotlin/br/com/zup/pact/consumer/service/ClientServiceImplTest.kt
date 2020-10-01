package br.com.zup.pact.consumer.service

import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.repository.ClientRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ClientServiceImplTest {
    private val clientRepositoryMock: ClientRepository = mockk()
    private val clientService: ClientService = ClientServiceImpl(clientRepositoryMock)

    @BeforeEach
    fun setUp() {
        clearMocks(clientRepositoryMock)
    }

    @Test
    fun `Method getClientDetails should return a ClientDetails from repository`() {
        every { clientRepositoryMock.findByClientId(1) }
                .returns(createClientDetails(1))

        val expectedClientDetails = clientService.getClientDetails(1)

        Assertions.assertThat(expectedClientDetails)
                .isEqualTo(createClientDetails(1))
    }

    @Test
    fun `Method getClientDetails should return null if no ClientDetails found`() {
        every { clientRepositoryMock.findByClientId(1) }
                .returns(null)

        val expectedClientDetails = clientService.getClientDetails(1)

        Assertions.assertThat(expectedClientDetails)
                .isNull()
    }

    private fun createClientDetails(
            clientId: Int,
            accountId: Int = 1,
            firstName: String = "any_first_name",
            lastName: String = "any_last_name",
            age: Int = 20
    ) = ClientDetailsDTO(
            clientId, accountId, firstName, lastName, age
    )

}