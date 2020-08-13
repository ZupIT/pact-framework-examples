# Exemplos de uso do Pact Framework
![Pact Logo](imgs/pact-logo.png)

Bem vindo ao repositório de exemplos do Pact Framework. <br>
O objetivo deste repositório é fornecer exemplos de implementações do [PACT Framework](https://docs.pact.io/) 
em diferentes linguagens como forma de facilitar seu entendimento. 

Não conhece o Pact? Na sessão de [links úteis](#links-uteis) você encontrará tudo que precisa para se interar do assunto. 

## <a name='exemplos'>Exemplos neste repositório</a>

Abaixo estão os links dos exemplos que você irá encontrar neste repositório.
Neles você também irá encontrar detalhes sobre como executá-los.

 - [Java/Spring Boot](example/java/spring-boot/);
 - [Java/Kafka](example/java/messaging-kafka/);
 - [Node/Express](example/node);

## <a name='estrutura-basica'>Estrutura básica dos testes</a>

Todos os exemplos neste repositório seguem, basicamente, o fluxo representado abaixo.

![Pact Workflow](imgs/pact-workflow.png)

O processo de validação dos contratos acontece da seguinte forma:

 - A API Consumidor cria em mantém um contrato (em acordo com o Provedor).

 - A API Consumidor verifica que está rodando de forma aderente ao contrato.
 
 - A API Consumidor publica o contrato para que o provedor possa acessá-lo.

 - A API Provedor verifica que está rodando de forma aderente ao contrato do(s) consumidor(es).

## <a name='links-uteis'>Links Úteis</a>

- Série de artigos Testes de contratos com PACT: <br />
https://www.zup.com.br/blog/testes-de-contratos-com-pact-1-conceitos
https://www.zup.com.br/blog/testes-de-contratos-com-pact-2-consumer-driven-contract
https://www.zup.com.br/blog/testes-de-contratos-com-pact-framework-3-hands-on

- Documentação do Pact: <br />
https://docs.pact.io/

- PactFlow: <br />
https://pactflow.io/

- Pact Benefits: <br />
https://pactflow.io/how-pact-works/?utm_source=ossdocs&utm_campaign=intro_animation#slide-1

- Pact Broker: <br />
https://github.com/pact-foundation/pact_broker


