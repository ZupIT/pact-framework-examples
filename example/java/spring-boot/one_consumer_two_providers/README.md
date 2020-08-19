# Exemplo em Java Spring Boot

Exemplo da criação de um Pact entre:
* 1 consumidor (Consumer)
* 2 provedor (Providera)

- Veja este mesmo cenário feito em [Node](../../../node/one_consumer_two_providers).

## Ferramentas

 - OpenJdk 11
 - Spring Boot
 - Swagger
 - Lombok
 - Pact JVM
 - Pact Broker
 
## Como executar

1. Garanta que você tenha uma instância do Pact Broker rodando localmente. 
Vide sessão [configuração do Pact Broker](../../../../README.md#config-broker) caso tenha dúvida.

2. Abra os projetos contido neste diretório (`client-api`, `prime-account-details-api` e `account-api`) em sua IDE de preferência e 
instale suas dependências. Se estiver usando o IntelliJ, você também precisará instalar o [Plugin do Lombok](https://projectlombok.org/setup/intellij).

3. Para gerar o contrato, basta rodar os testes Junit do projeto `client-api`. <br>
Obtendo sucesso, o plugin maven do Pact irá gerar um arquivo json contendo o contrato entre as APIs.
Veja os exemplos nas imagens abaixo.

<img src="../../../../imgs/junit5-tests-runner.png" alt="Pact Runner Tests"/>

<img src="../../../../imgs/pact-contract-generated.png" alt="Pact Contract Generated"/>

4. Com o contrato gerado, podemos publicá-lo no Pact Broker. 
Para isto, podemos utilizar o plugin maven do Pact. <br>
É necessário confirmar que o plugin está configurado corretamente.

<img src="../../../../imgs/pact-maven-plugin.png" alt="Pact Maven Plugin"/>

Após confirmar o status do plugin, abra outro terminal no diretório `client-api` e execute o seguinte comando:

```
mvn pact:publish
```

Em seguida, você poderá ver o contrato publicado no Pact Broker (`http://localhost:9292`).

5. Com o contrato publicado no Broker, agora validaremos se a API provedora (provider) 
está aderente ao contrato.

No projeto `account-api`, rode os testes Junit e verifique se eles são concluídos com sucesso. <br>
Este teste irá verificar no Broker os contratos disponiveis para validação, baixá-los e testá-los de acordo com a API provedora.

Ao final, podemos conferir o resultado do teste que é publicado no Broker. 

6. Para validar o contrato do segunda API provedora, bas segui o mesmo processo do item anterior, mas desta vez com o projeto `prime-account-details-api`