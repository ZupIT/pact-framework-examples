package br.com.zup.pact.consumer.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.exception.ClientNotFoundException
import br.com.zup.pact.consumer.integration.account.service.AccountIntegrationService
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
    private val accountIntegrationServiceMock: AccountIntegrationService = mockk()
    private val clientService: ClientService = ClientServiceImpl(
            clientRepositoryMock,
            accountIntegrationServiceMock
    )

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

    @Test
    fun `Method getAll should returns a ClientDetails list`() {

        every { clientRepositoryMock.getAll() }
                .returns(mutableListOf(
                        createClientDetails(1),
                        createClientDetails(2),
                        createClientDetails(3)
                ))

        val actualReturn: List<ClientDetailsDTO>? = clientService.getAll()

        Assertions.assertThat(actualReturn).containsExactly(
                createClientDetails(1),
                createClientDetails(2),
                createClientDetails(3)
        )
    }

    @Test
    fun `Method getAll should return a list empty if ClientDetails list is empty`() {

        every { clientRepositoryMock.getAll() }
                .returns(mutableListOf())

        val actualReturn: List<ClientDetailsDTO>? = clientService.getAll()

        Assertions.assertThat(actualReturn).isEmpty()
    }

    @Test
    fun `Method getBalance should return a BalanceDTO`() {

        every { clientRepositoryMock.findByClientId(1) }
                .returns(ClientDetailsDTO(1, 2, "any", "any", 19))

        every { accountIntegrationServiceMock.getBalance(2) }
                .returns(BalanceDTO(2, 1, 100.0))

        val expectedBalanceDTO = clientService.getBalance(1)

        Assertions.assertThat(expectedBalanceDTO).isEqualTo(BalanceDTO(2, 1, 100.0))
    }

    @Test
    fun `Method getBalance should throw ClientNotFoundException when ClientDetails is null`() {
        every { clientRepositoryMock.findByClientId(1) }
                .returns(null)

        Assertions.assertThatExceptionOfType(ClientNotFoundException::class.java)
                .isThrownBy { clientService.getBalance(1) }
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