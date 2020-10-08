# Exemplo em Node gRPC

Exemplo da criação de um Pact entre:

- 1 consumidor (account-api)
- 1 provedor (client-api)

## Ferramentas

- Node + npm
- Express
- Typescript
- Pact
- Pact Broker
- grpc-js
- Jest


## Cenário

Este exemplo aborda um cenário comum no setor bancário.
Nosso objetivo é obter o dado de saldo de determinado cliente. 
Para isto, vamos considerar que este dado será recuperado da seguinte forma:

1 - Com o identificador do cliente, solicitamos ao serviço de dominio do cliente (client-api) o valor do saldo em conta. <br>
2 - Por sua vez, o client-api pergunta ao serviço de domínio da conta (account-api) qual o saldo contido na conta atrelada aquele cliente. <br>
3 - Tendo a informação do saldo em conta, o client-api retorna a informação a quem a solicitou.

De forma resumida, temos os seguintes serviços:

* account-api: mantém e gerencia informações relacionadas a contas bancárias.
* client-api: mantém e gerencia informações sobre clientes/correntistas.

A imagem abaixo representa esta interação que acabamos de definir.

<img src="../../../imgs/client_account_findbyid_scenario.png" alt="gRPC pact scenario"/>


## Compatibilidade com gRPC

Atualmente, o Pact suporta cenários de integração via REST, Mensageria e GraphQL. 
No entanto, como podemos ver no [roadmap do framework](https://pact.canny.io/feature-requests/p/support-protobufs), o suporte oficial para gRPC já está planejado.
Enquanto esperamos este novo recurso, preparamos uma solução para que você possa iniciar seus testes desde já,
de forma que seja fácil migrar para uma solução definitiva no futuro. 

De forma bastante resumida, esta solução define padrões para representarmos cada parte do contrato gRPC em REST, 
possibilitando assim a validação dos contratos. A seguir explicamos a solução de forma mais detalhada. 

Como dizemos acima, hoje o Pact não tem suporte para protocolo gRPC, mas e se conseguissemos 
transformar, ou representar, esta chamada em outro protocolo que o Pact entenda (ex: REST) ? 
É exatamente esta a abordagem adotada aqui. 

* ### Solução no lado do Consumidor

Quando uma chamada é feita no lado do consumidor, assim como em frameworks REST, o gRPC nos possibilita 
interceptá-la através da definição de um **Interceptor**. Desta forma, conseguimos obter informações destas chamadas, incluindo dados definidos no protofile, como **package**, **service** e **method**. A partir destas informações conseguimos 
montar uma chamada REST, baseado na seguinte convenção:

```
POST http://{{address}}/grpc/{{packagaName}}.{{service}}/{{method}}
``` 

Tendo gerado então esta chamada REST, conseguimos seguir o fluxo de teste do Pact no lado do consumidor. 
Para maiores detalhes sobre a implementação dos testes, vide o arquivo de teste em [client-api/src/tests/pact.spec.ts](./client-api/src/tests/pact.spec.ts). 

* ### Solução no lado do Provedor

De forma análoga a solução proposta no lado do Consumidor, no lado do Provedor precisamos fazer a tradução entre a chamada gRPC e REST, mas de forma inversa (REST > gRPC). 
Para isto, precisamos criar um servidor REST como **proxy**, que escute as chamadas que definimos no lado do consumidor e as encaminhe para o servider gRPC. 
Neste caso utilizamos Express para criar implementarmos este **proxy* seguindo a convenção definida anteriormente.
Para maiores detalhes sobre a implementação dos testes, vide o arquivo de teste em [account-api/src/tests/verify-pact.spec.ts](./client-api/src/tests/verify-pact.spec.ts). 

A imagem a seguir representa esta solução:

<img src="../../../imgs/node_pact_grpc_solution.png" alt="Node Pact gRPC solution"/>

## Como executar

Aqui, temos o passo a passo para conseguirmos averiguar o cenário acima.

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
