# Exemplo em Node

Exemplo da criação de um pacto entre:
* 2 consumidores (Consumers)
* 1 provedor (Provider)

- Veja este mesmo cenário feito em Java: (../../java/spring-boot/two_consumers_one_producer).

## Ferramentas

- Docker Compose
- Express
- Typescript
- Jest
- Pact

## Passo a passo

1. Garanta que você tenha uma instância do Pact Broker rodando localmente. 
Para isto, vide a sessão de [configuração do Pact Broker](../../../README.md#config-broker).


4. Com o Broker funcionando, podemos iniciar os testes.
Primeiro, precisamos instalar as dependencias da API. Para isto, va até o diretório `client-api` e execute o seguinte comando:

```shell
npm install
```
Em seguida, precisamos gerar o contrato do PACT para a nossa API consumidora e o publicamos no Broker. <br>
No mesmo diretório, execute os seguintes comandos:

```shell
yarn test
```

```shell
yarn pact:publish
```

Acesse o Pact Broker (`http://localhost`) em seu navegador. Você deverá ver o contrato publicado.

<img src="../../../imgs/new-pact-contract.png" alt="new pact contract"/>

Caso tenha interesse, o contrato gerado pode ser conferido no diretório `client-api/pacts`.

5. Para criar o contrato do segundo consumidor, siga o mesmo processo da etapa anterior no diretório 
`legal-person-clent`.

6. Para validar o contrato gerado, vamos até o diretório da nossa API provedora (provider) `account-api`.
Novamente, precisamos instalar as dependencias da API. Para isto, execute o seguinte comando:

```shell
npm install
```

Para testarmos o contrato com a API consumidora, precisamos apenas rodar o teste da API.
Para isto, execute:

```shell
yarn test
```

Este teste irá verificar no Broker os contratos disponiveis para validação, baixá-los e testá-los de acordo com a API.
Ao final, podemos conferir o resultado do teste que é publicado no Broker. 

<img src="../../../imgs/validated-pact-contract.png" alt="new pact contract"/>

**Ao final, entre com CTRL + C para liberar o terminal**