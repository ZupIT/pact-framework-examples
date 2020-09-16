package br.com.zup.pact.consumer.dto

import java.io.Serializable
import java.math.BigDecimal

class BalanceDTO(val clientId: Int, val accountId: Int, val balance: BigDecimal): Serializable