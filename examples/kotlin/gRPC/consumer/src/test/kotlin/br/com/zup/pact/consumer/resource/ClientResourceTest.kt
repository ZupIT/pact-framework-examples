package br.com.zup.pact.consumer.resource

import br.com.zup.pact.client.resource.BalanceResponse
import br.com.zup.pact.client.resource.ClientRequest
import br.com.zup.pact.client.resource.ClientResponse
import br.com.zup.pact.client.resource.EmptyRequest
import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.service.ClientService
import io.grpc.stub.StreamObserver
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ClientResourceTest {
    private val clientServiceMock: ClientService = mockk()
    private val clientResource: ClientResourceImpl = ClientResourceImpl(clientServiceMock)

    @BeforeEach
    fun setUp() {
        clearMocks(clientServiceMock)
    }

    @Test
    fun `Method getAll should return getAll from the service`() {

        every { clientServiceMock.getAll() }
                .returns(listOf(ClientDetailsDTO(
                        1,
                        2,
                        "any_name",
                        "any_final_name",
                        20
                )))

        val responseObserver: StreamObserver<ClientResponse> = mockk()
        every { responseObserver.onNext(any()) } just Runs
        every { responseObserver.onCompleted() } just Runs

        clientResource.getAll(
                EmptyRequest.newBuilder().build(),
                responseObserver
        )

        verify(exactly = 1) {
            responseObserver.onNext(
                    ClientResponse.newBuilder()
                            .setId(1)
                            .setAccountId(2)
                            .setName("any_name")
                            .setFinalName("any_final_name")
                            .setAge(20)
                            .build()
            )
        }

        verify(exactly = 1) {
            responseObserver.onCompleted()
        }
    }

    @Test
    fun `Method findById should return a ClientDetails from the service`() {
        every { clientServiceMock.getClientDetails(any()) }
                .returns(ClientDetailsDTO(
                        1,
                        2,
                        "any_name",
                        "any_final_name",
                        20
                ))

        val responseObserver: StreamObserver<ClientResponse> = mockk()

        every { responseObserver.onNext(any()) } just Runs
        every { responseObserver.onCompleted() } just Runs

        clientResource.getClientById(
                ClientRequest.newBuilder().setClientId(1).build(),
                responseObserver
        )

        verify(exactly = 1) {
            responseObserver.onNext(
                    ClientResponse.newBuilder()
                            .setId(1)
                            .setAccountId(2)
                            .setName("any_name")
                            .setFinalName("any_final_name")
                            .setAge(20)
                            .build()
            )
        }

        verify(exactly = 1) {
            responseObserver.onCompleted()
        }
    }

    @Test
    fun `Method getBalance should return a Balance from the service`() {
        every { clientServiceMock.getBalance(any()) }
                .returns(BalanceDTO(
                        1,
                        100.0
                ))

        val responseObserver: StreamObserver<BalanceResponse> = mockk()

        every { responseObserver.onNext(any()) } just Runs
        every { responseObserver.onCompleted() } just Runs

        clientResource.getBalance(
                ClientRequest.newBuilder().setClientId(1).build(),
                responseObserver
        )

        verify(exactly = 1) {
            responseObserver.onNext(
                    BalanceResponse.newBuilder()
                            .setAccountId(1)
                            .setBalance(100.0)
                            .build()
            )
        }

        verify(exactly = 1) {
            responseObserver.onCompleted()
        }
    }
}