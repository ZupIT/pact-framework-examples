# Exemplo em Java com Kafka

Exemplo da criação de um Pact entre:
* 1 consumidor (Consumer)
* 1 provedor (Provider)

## Ferramentas

- OpenJdk 13
- Spring Boot
- Swagger
- Spring Kafka
- Pact JVM
- Pact Broker
- Maven

## Índice

<!--ts-->

- [Cenários](#Cenários)
- [Como executar](#Como-executar)
<!--ts -->



## Como executar

1. Garanta que você tenha uma instância do Pact Broker rodando localmente. 
Vide sessão [configuração do Pact Broker](../../../README.md#config-broker) caso tenha dúvida.

2. Abra os projetos contido neste diretório (`pact-messsage-consumer` e `pact-message-provider`) em sua IDE de preferência e 
instale suas dependências.

3. Com o Broker funcionando, podemos iniciar os testes.
Para gerar o contrato, basta rodar os testes Junit do projeto `pact-messsage-consumer`. <br>
Obtendo sucesso, o plugin maven do Pact irá gerar um arquivo json contendo o contrato entre as APIs.

<img src="../../../imgs/junit5-tests-runner.png" alt="Pact Runner Tests"/>

<img src="../../../imgs/pact-contract-generated.png" alt="Pact Contract Generated"/>

4. Com o contrato gerado, podemos publicá-lo no Pact Broker. 
Para isto, podemos utilizar o [plugin maven do Pact](https://mvnrepository.com/artifact/au.com.dius/pact-jvm-provider). <br>
É necessário confirmar que o plugin está configurado corretamente.

<img src="../../../imgs/pact-maven-plugin.png" alt="Pact Maven Plugin"/>

Após confirmar o status do plugin, abra outro terminal no diretório `pact-messsage-consumer` e execute o seguinte comando:

```
mvn pact:publish
```

Em seguida, você poderá ver o contrato publicado no Pact Broker (`http://localhost:9292`).

5. Com o contrato publicado no Broker, agora validaremos se a API provedora (provider) 
está aderente ao contrato.

No projeto `pact-message-provider`, rode os testes Junit e verifique se eles são concluídos com sucesso. <br>
Este teste irá verificar no Broker os contratos disponiveis para validação, baixá-los e testá-los de acordo com a API provedora.

Ao final, podemos conferir o resultado do teste que é publicado no Broker. 