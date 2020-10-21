package br.com.zup.pact.consumer.integration.account.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.provider.resource.AccountResourceGrpc
import br.com.zup.pact.provider.resource.AccountResponse
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AccountIntegrationImplTest {

    private val grpcServiceMock: GrpcService = mockk()
    private val accountIntegrationImpl: AccountIntegrationImpl = AccountIntegrationImpl(grpcServiceMock)

    @BeforeEach
    fun setUp() {
        clearMocks(grpcServiceMock)
    }

    @Test
    fun `Method getBalance should return the balanceDTO`() {

        val stubMock: AccountResourceGrpc.AccountResourceBlockingStub = mockk()
        every { stubMock.findById(any()) }
                .returns(
                        AccountResponse
                                .newBuilder()
                                .setAccountId(10)
                                .setBalance(100.0)
                                .build()
                )

        every { grpcServiceMock.getAccountResourceBlockingStub() }
                .returns(stubMock)

        val actualReturn = accountIntegrationImpl.getBalance(1)

        Assertions.assertThat(actualReturn)
                .isEqualTo(BalanceDTO(10, 100.0))
    }

}