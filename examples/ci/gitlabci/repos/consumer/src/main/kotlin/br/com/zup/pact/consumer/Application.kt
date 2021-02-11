package br.com.zup.pact.consumer

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("br.com.zup.pact.consumer")
		.start()
}

