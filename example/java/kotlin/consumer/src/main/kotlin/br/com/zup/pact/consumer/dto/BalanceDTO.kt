package br.com.zup.pact.consumer.dto

data class BalanceDTO(
        val clientId: Int,
        val accountId: Int,
        val balance: Double
)