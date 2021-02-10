package br.com.zup.pact.consumer.dto

data class BalanceDTO (
        val accountId: Int,
        val clientId: Int,
        val balance: Double
)