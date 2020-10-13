package br.com.zup.pact.provider.resource

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.enums.AccountType
import br.com.zup.pact.provider.service.AccountService
import io.grpc.stub.StreamObserver
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AccountResourceImplTest {

    private val accountServiceMock: AccountService = mockk()
    private val accountResourceImpl: AccountResourceImpl = AccountResourceImpl(accountServiceMock)

    @BeforeEach
    fun setUp() {
        clearMocks(accountServiceMock)
    }

    @Test
    fun `Method getAll should return getAll from the service`() {

        every { accountServiceMock.getAll() }
                .returns(listOf(AccountDetailsDTO(
                        accountId = 1,
                        balance = 100.0,
                        accountType = AccountType.MASTER
                )))

        val responseObserver: StreamObserver<AccountDetailsResponse> = mockk()
        every { responseObserver.onNext(any()) } just Runs
        every { responseObserver.onCompleted() } just Runs


        accountResourceImpl.getAll(
                EmptyRequest.newBuilder().build(),
                responseObserver
        )


        verify(exactly = 1) {
            responseObserver.onNext(
                    AccountDetailsResponse.newBuilder()
                            .setAccountId(1)
                            .setBalance(100.0)
                            .setAccountType(AccountType.MASTER.toString())
                            .build()
            )
        }

        verify(exactly = 1) {
            responseObserver.onCompleted()
        }

    }

    @Test
    fun `Method findById should return findById from the service`() {

        every { accountServiceMock.findByAccountId(any()) }
                .returns(BalanceDTO(
                        accountId = 1,
                        balance = 100.0
                ))

        val responseObserver: StreamObserver<AccountResponse> = mockk()
        every { responseObserver.onNext(any()) } just Runs
        every { responseObserver.onCompleted() } just Runs


        accountResourceImpl.findById(
                AccountIdRequest.newBuilder().setAccountId(1).build(),
                responseObserver
        )


        verify(exactly = 1) {
            responseObserver.onNext(
                    AccountResponse.newBuilder()
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