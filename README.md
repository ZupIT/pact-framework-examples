![Pact Logo](imgs/pact-logo.png)
# Exemplos de uso de Testes de Contrato com Pact Framework

Bem vindo ao repositório de exemplos do Pact Framework!! <br><br>
O objetivo deste repositório é demonstrar como fazer testes de contrato com o [PACT Framework](https://docs.pact.io/) em diferentes linguagens como forma de facilitar seu entendimento. 

O teste de contrato é uma abordagem utilizada para garantir uma integração entre dois sistemas. A maior vantagem da abordagem é o teste via artefato que garante que as duas pontas se validem na forma de um teste unitário. Esse teste unitário é executado para realizar o build dos dois artefatos, garantindo assim que ambas as pontas estejam de acordo com o contrato entre as duas partes. 

O Pact Framework realiza testes de contrato com: **Rest, Mensageria e GRPC**<br>
As integrações não necessitam ter sido desenvolvidas na mesma linguagem para que você faça o teste de contrato, ele é agnóstico a linguagem.

Caso não conheça o Pact e o conceito de testes de contratos, dê uma olhada na sessão de [links úteis](#links-uteis). 

----
Linguagens suportadas pelo Pact:

<img src="https://img.shields.io/badge/java-%23ED8B00.svg?&style=for-the-badge&logo=java&logoColor=white"/> <img src="https://img.shields.io/badge/javascript%20-%23323330.svg?&style=for-the-badge&logo=javascript&logoColor=%23F7DF1E"/> <img src="https://img.shields.io/badge/kotlin-%230095D5.svg?&style=for-the-badge&logo=kotlin&logoColor=white"/> <img src="https://img.shields.io/badge/ruby-%23CC342D.svg?&style=for-the-badge&logo=ruby&logoColor=white"/> <img src="https://img.shields.io/badge/go-%2300ADD8.svg?&style=for-the-badge&logo=go&logoColor=white"/> <img src="https://img.shields.io/badge/php-%23777BB4.svg?&style=for-the-badge&logo=php&logoColor=white"/> <img src="https://img.shields.io/badge/c%23%20-%23239120.svg?&style=for-the-badge&logo=c-sharp&logoColor=white"/>
<img src="https://img.shields.io/badge/python%20-%2314354C.svg?&style=for-the-badge&logo=python&logoColor=white"/>

----
Linguagens abordadas nos exemplos:

<img src="https://img.shields.io/badge/java-%23ED8B00.svg?&style=for-the-badge&logo=java&logoColor=white"/> <img src="https://img.shields.io/badge/javascript%20-%23323330.svg?&style=for-the-badge&logo=javascript&logoColor=%23F7DF1E"/> <img src="https://img.shields.io/badge/kotlin-%230095D5.svg?&style=for-the-badge&logo=kotlin&logoColor=white"/>

----
Zuppers contribuidores

[![Contributors Display](https://badges.pufler.dev/contributors/ZupIT/pact-framework-examples?size=60&padding=5&bots=true)](https://badges.pufler.dev)

## <a name='exemplos'>Exemplos neste repositório</a>

Abaixo estão os links dos exemplos que você irá encontrar neste repositório.
Neles você também irá encontrar detalhes sobre como executá-los.

 - **Backend**
   - **Java**
      * [Rest - um consumidor e um provedor](example/java/spring-boot/one_consumer_one_provider).
      * [Rest - um consumidor e dois provedores](example/java/spring-boot/one_consumer_two_providers).
      * [Rest - dois consumidores e um provedor](example/java/spring-boot/two_consumers_one_provider).
      * [Mensageria/Kafka](example/java/messaging-kafka/)
 - **FrontEnd**
   - **Express**
      * [Rest - um consumidor e um provedor](example/node/one_consumer_one_provider).
      * [Rest - um consumidor e dois provedores](example/node/one_consumer_two_providers).
      * [Rest - dois consumidores e um provedor](example/node/two_consumers_one_provider).
   - **Angular**
      * [Rest - um consumidor e um provedor](example/frontend).
- **CI/CD**
   * [Rest - um consumidor e um provedor com verificação de integração automatizada com Jenkins](example/ci/jenkins).

## <a name='estrutura-basica'>Estrutura básica dos exemplos</a>

Abaixo um desenho que representa bem como o teste de contrato é realizado.

![Pact Workflow](imgs/pact-workflow.png)

O processo de validação dos contratos acontece da seguinte forma:

 - O **Consumer** cria um contrato em forma de testes BDD (Behavior Driven Development) com suas expectativas de consumo da integração com o Provider.

 - O **Consumer**  testa suas expectativas de Consumer sobre o Provider dentro do seu próprio teste, sem necessidade de realizar comunicação com o Provider real.
 
 - O **Consumer** publica o contrato no Broker do Pact para que o Provider possa acessá-lo e testá-lo.

 - O **Provider** cria um código de teste unitário que obterá o contrato do Pact Broker e dado as expectativas que o Consumer descreveu, rodará em seu próprio endpoint para validar se condiz com o que esse Provider está fornecendo (Tudo isso em tempo de teste unitário) e sem a necessidade de se comunicar com o Consumer. Usando apenas o contrato gerado pelo Pact.

 A vantagem dessa abordagem é que todos os testes são escritos em forma de teste unitário, portanto, é fácil automatizar a validação das integrações em uma esteira de CI, onde ela rodará seus testes e validará seus contratos de integração, assim, garantindo que suas integrações estão funcionando antes de você publicar seu artefato em qualquer ambiente. O teste no artefato assegura que nenhuma mudança seja feita sem o conhecimento de ambos os lados, isso ajuda a prevenir problemas causados por falha de comunicação entre equipes de integração de sistemas.

O Pact Broker é uma ferramenta fundamental para o gerenciamento dos contratos entre as integrações envolvidas. Ele mantém o contrato, a data de atualização e o status dessa integração, para mais informações acesse [Pact Broker](https://github.com/pact-foundation/pact_broker). <br>

Para que você consiga utilizar o teste de contrato com o Pact, sugerimos que você tenha um container com o Pact Broker em uma infraestrutura acessível para as duas aplicações que fazem a integração.

Para qualquer um dos exemplos deste repositório, precisaremos de uma instância do Broker. 
A seguir explicamos como subir o seu próprio Broker. 

## <a name='config-broker'> Informações do Pact Broker dos exemplos</a>

Para configurar uma instancia do Pact Broker em sua máquina local, clone o projeto em sua máquina, <br>
vá até o diretório `infrastructure/pact-broker` e execute o seguinte comando:

> Caso o exemplo for relacionado a Integração Continua, utilize o diretõrio `infrastructure/pact-broker-with-jenkins`

```shell
docker-compose up
```
Para validar se o broker está funcionando corretamente, acesse o endereço [http://localhost:9292](http://localhost:9292). <br>
Caso tudo tenha ocorrido conforme planejado, você verá a página inicial do Pact Broker.

Se estiver utilizando o compose com Jenkins, você poderá acessá-lo no endereço [http://localhost:8080](http://localhost:8080)

Credenciais para acesso ao Jenkins:
```bash
user:admin
pass:admin
```

> Caso tenha alguma dificuldade para subir o container `jenkins_pact_broker`, verifique se o diretório `jenkins_home` possui permissões de escrita. 
> Caso não tenha permissão, podemos conceder da seguinte forma:
```
sudo chown $USER:$USER jenkins_home -R
```

Para demais informações, acesse a [documentação do Jenkins](https://www.jenkins.io/doc/).

## <a name='links-uteis'>Links Úteis</a>

- Conceitos do PACT: <br />
https://www.zup.com.br/blog/testes-de-contratos-com-pact-1-conceitos

- Conceitos do contratos dirigidos a consumidores (Consumer Driven Contract): <br />
https://www.zup.com.br/blog/testes-de-contratos-com-pact-2-consumer-driven-contract

- Hands On com Pact: <br />
https://www.zup.com.br/blog/testes-de-contratos-com-pact-framework-3-hands-on

- Pact com CI: <br />
https://www.zup.com.br/blog/testes-de-contratos-com-pact-framework-4-pact-com-ci

- Documentação do Pact: <br />
https://docs.pact.io/

- PactFlow: <br />
https://pactflow.io/

- Pact Benefits: <br />
https://pactflow.io/how-pact-works/?utm_source=ossdocs&utm_campaign=intro_animation#slide-1

- Pact Broker: <br />
https://github.com/pact-foundation/pact_broker

- Para mais exemplos: <br />
https://github.com/pact-foundation?q=workshop

