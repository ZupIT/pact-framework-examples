package br.com.zup.pact.provider.dto

data class BalanceDTO (
        val clientId: Int,
        val accountId: Int,
        val balance: Double
)