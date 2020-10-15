package br.com.zup.pact.consumer.resource

import br.com.zup.pact.client.resource.ClientResponse
import br.com.zup.pact.client.resource.EmptyRequest
import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.service.ClientService
import io.grpc.stub.StreamObserver
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
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

        verify (exactly = 1) {
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

        verify (exactly = 1) {
            responseObserver.onCompleted()
        }
    }

}