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
import java.util.*

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
    fun `Method getClientDetails should return findByClientId from the repository`() {

        every { clientRepositoryMock.findByClientId(1) }
                .returns(Optional.of(createClientDetailsDTO(accountId = 1)))

        val expectedAccountDetailsDTO = clientService.getClientDetails(1)

        Assertions.assertThat(expectedAccountDetailsDTO).isEqualTo(
                Optional.of(createClientDetailsDTO(accountId = 1))
        )
    }

    @Test
    fun `Method getAll should return getAll from the repository`() {

        every { clientRepositoryMock.getAll() }
                .returns(listOf(createClientDetailsDTO(accountId = 1)))

        val expectedAccountDetailsDTO = clientService.getAll()

        Assertions.assertThat(expectedAccountDetailsDTO).isEqualTo(
                listOf(createClientDetailsDTO(accountId = 1))
        )
    }

    @Test
    fun `Method getBalance should return Optional of BalanceDTO`() {

        every { clientRepositoryMock.findByClientId(1) }
                .returns(Optional.of(createClientDetailsDTO(accountId = 2)))

        every { accountIntegrationServiceMock.getBalance(2) }
                .returns(Optional.of(BalanceDTO(
                        accountId = 2,
                        balance = 100.0
                )))

        val expectedBalanceDTO = clientService.getBalance(1)

        Assertions.assertThat(expectedBalanceDTO).isEqualTo(
                Optional.of(BalanceDTO(
                        accountId = 2,
                        balance = 100.0
                ))
        )
    }

    @Test
    fun `Method getBalance should throw ClientNotFoundException when ClientDetails is null`() {

        every { clientRepositoryMock.findByClientId(1) }
                .returns(Optional.empty())

        Assertions.assertThatExceptionOfType(ClientNotFoundException::class.java)
                .isThrownBy { clientService.getBalance(1) }
    }

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