package br.com.zup.pact.provider.dto

import java.math.BigDecimal

data class BalanceDTO(
        val clientId: Int,
        val accountId: Int,
        val balance: Double
)