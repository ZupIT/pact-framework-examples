package br.com.zup.pact.provider

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("br.com.zup.pact.provider")
		.start()
}

