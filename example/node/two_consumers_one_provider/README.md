# Exemplo em Node

Exemplo da criação de um Pact entre:

- 2 consumidores (Consumers)
- 1 provedor (Provider)

* Veja este mesmo cenário feito em [Java](../../java/spring-boot/two_consumers_one_provider).

## Ferramentas

- Node + npm
- Express
- Typescript
- Jest
- Pact
- Pact Broker

## Como executar

1. Garanta que você tenha uma instância do Pact Broker rodando localmente.
   Vide sessão [configuração do Pact Broker](../../../README.md#config-broker) caso tenha dúvida.

2. Com o Broker funcionando, podemos iniciar os testes.
   Primeiro, precisamos instalar as dependencias da API. Para isto, va até o diretório `common-person-client` e execute o seguinte comando:

```shell
npm install
```

Em seguida, precisamos gerar o contrato do PACT para a nossa API consumidora e o publicamos no Broker. <br>
No mesmo diretório, execute os seguintes comandos:

```shell
npm run test
```

```shell
npm run pact:publish
```

Acesse o Pact Broker (`http://localhost:9292`) em seu navegador. Você deverá ver o contrato publicado.

<img src="../../../imgs/new-pact-contract.png" alt="new pact contract"/>

Caso tenha interesse, o contrato gerado pode ser conferido no diretório `common-person-client/pacts`.

3. Para criar o contrato do segundo consumidor, siga o mesmo processo da etapa anterior no diretório
   `legal-person-clent`.

4. Para validar o contrato gerado, vamos até o diretório da nossa API provedora (provider) `account-api`.
   Novamente, precisamos instalar as dependencias da API. Para isto, execute o seguinte comando:

```shell
npm install
```

Para testarmos o contrato com a API consumidora, precisamos apenas rodar o teste da API.
Para isto, execute:

```shell
npm run test
```

Este teste irá verificar no Broker os contratos disponiveis para validação, baixá-los e testá-los de acordo com a API.
Ao final, podemos conferir o resultado do teste que é publicado no Broker.

<img src="../../../imgs/validated-pact-contract.png" alt="new pact contract"/>
