# Exemplo em Node

Exemplo da criação de um Pact entre:

- 1 consumidor (Consumer)
- 2 provedores (Providers)

* Veja este mesmo cenário feito em [Java](../../java/spring-boot/one_consumer_two_providers).

## Ferramentas

- Node + npm
- Express
- Typescript
- Jest
- Pact
- Pact Broker

## Cenários

Este exemplo aborda um cenário comum no setor bancário.
Nosso objetivo é obter o dado de saldo de determinado cliente e/ou obter o dado da porcentagem de desconto de determinada conta.
Para isto, vamos considerar que este dado será recuperado da seguinte forma:

### Obtendo saldo

1 - Com o identificador do cliente, solicitamos ao serviço de dominio do cliente (client-api) o valor do saldo em conta. <br>
2 - Por sua vez, o client-api pergunta ao serviço de domínio da conta (account-api) qual o saldo contido na conta atrelada aquele cliente. <br>
3 - Tendo a informação do saldo em conta, o client-api retorna a informação a quem a solicitou.

A imagem abaixo representa este fluxo.

<img src="../../../imgs/get-balance-node.png" alt="new pact contract"/>

### Obtendo a porcentagem de desconto

1 - Com o identificador do cliente, solicitamos ao serviço de dominio do cliente (client-api) a porcentagem de desconto. <br>
2 - Por sua vez, o client-api pergunta ao serviço de domínio da conta (prime-account-details-api) qual a porcentagem de desconto que aquele cliente tem direito. <br>
3 - Tendo a informação da porcentagem de desconto, o client-api retorna a informação a quem a solicitou.

A imagem abaixo representa este fluxo.

<img src="../../../imgs/get-prime-account-details-node.png" alt="new pact contract"/>

De forma resumida, temos os seguintes serviços:

- account-api: mantém e gerencia informações relacionadas a contas bancárias.
- prime-account-details-api: mantém e gerencia informações relacionadas a porcentagem de desconto das contas.
- client-api: mantém e gerencia informações sobre clientes/correntistas.

## Como executar

1. Garanta que você tenha uma instância do Pact Broker rodando localmente.
   Vide sessão [configuração do Pact Broker](../../../README.md#config-broker) caso tenha dúvida.

2. Com o Broker funcionando, podemos iniciar os testes.
   Primeiro, precisamos instalar as dependencias da API. Para isto, va até o diretório `client-api` e execute o seguinte comando:

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

Caso tenha interesse, o contrato gerado pode ser conferido no diretório `client-api/pacts`.

3. Para validar o contrato gerado, vamos até o diretório da nossa API provedora (provider) `account-api`.
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

4. Para executar o teste da segunda API provedora, siga o mesmo processo da etapa anterior no diretório `prime-account-details-api`.
