package br.com.zup.pact.consumer.exception

class ClientNotFoundException : RuntimeException {
    constructor(message: String) : super(message)
}
