![Pact Logo](imgs/pact-logo.png)
# Exemplos de uso do Pact Framework

Bem vindo ao repositório de exemplos do Pact Framework. <br>
O objetivo deste repositório é fornecer exemplos de implementações do [PACT Framework](https://docs.pact.io/) 
em diferentes linguagens como forma de facilitar seu entendimento. 

Caso não conheça o Pact e o conceito de testes de contratos, dê uma olhada na sess]ão de [links úteis](#links-uteis). 

## <a name='exemplos'>Exemplos neste repositório</a>

Abaixo estão os links dos exemplos que você irá encontrar neste repositório.
Neles você também irá encontrar detalhes sobre como executá-los.

 - [Java/Spring Boot](example/java/spring-boot/);
 - [Java/Kafka](example/java/messaging-kafka/);
 - [Node/Express](example/node);

## <a name='estrutura-basica'>Estrutura básica dos exemplos</a>

Todos os exemplos neste repositório seguem, basicamente, o fluxo representado abaixo.

![Pact Workflow](imgs/pact-workflow.png)

O processo de validação dos contratos acontece da seguinte forma:

 - A API Consumidor cria em mantém um contrato (em acordo com o Provedor).

 - A API Consumidor verifica que está rodando de forma aderente ao contrato.
 
 - A API Consumidor publica o contrato para que o provedor possa acessá-lo.

 - A API Provedor verifica que está rodando de forma aderente ao contrato do(s) consumidor(es).

Os contratos são publicados em uma aplicação que chamamos de Pact Broker. <br>
Para qualquer um dos exemplos deste repositório, precisaremos de uma instância desta aplicação.
A seguir explicamos como subir o seu próprio broker. 

## <a name='config-broker'> Configurando o Pact Broker </a>

Para configurar uma instancia do Pact Broker em sua máquina local, clone o projeto em sua máquina, <br>
vá até o diretório `infrastructure/pact-broker` e execute o seguinte comando:

```shell
docker-compose up
```
Para validar se o broker está funcionando corretamente, acesse o endereço [localhost](http:localhost). <br>
Caso tudo tenha ocorrido conforme planejado, você verá a página inicial do Pact Broker.

## <a name='links-uteis'>Links Úteis</a>

- Conceitos do PACT: <br />
https://www.zup.com.br/blog/testes-de-contratos-com-pact-1-conceitos

- Conceitos do contratos dirigidos a consumidores (Consumer Driven Contract): <br />
https://www.zup.com.br/blog/testes-de-contratos-com-pact-2-consumer-driven-contract

- Hands On com Pact: <br />
https://www.zup.com.br/blog/testes-de-contratos-com-pact-framework-3-hands-on

- Documentação do Pact: <br />
https://docs.pact.io/

- PactFlow: <br />
https://pactflow.io/

- Pact Benefits: <br />
https://pactflow.io/how-pact-works/?utm_source=ossdocs&utm_campaign=intro_animation#slide-1

- Pact Broker: <br />
https://github.com/pact-foundation/pact_broker


