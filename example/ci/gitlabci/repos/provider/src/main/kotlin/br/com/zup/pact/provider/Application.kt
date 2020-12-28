package br.com.zup.pact.provider

import io.micronaut.runtime.Micronaut.build

object Application {

	@JvmStatic
	fun main(args: Array<String>) {
		build()
				.args(*args)
				.packages("br.com.zup.pact.provider")
				.mainClass(Application.javaClass)
				.start()
	}
}



