# Exemplo em Kotlin Spring Boot

Exemplo da criação de um Pact entre:
* 1 consumidor (Consumer)
* 1 provedor (Provider)

- Veja este mesmo cenário feito em [Java](../../java/spring-boot/one_consumer_one_provider).

## Ferramentas

 - OpenJdk 11
 - Kotlin 1.3.72
 - Spring Boot
 - Pact JVM
 - Pact Broker
 - Maven

 ## Índice

<!--ts-->

- [Cenário](#Cenário)
- [Como executar](#Como-executar)

<!--ts -->

## Cenário

Este exemplo aborda um cenário comum no setor bancário.
Nosso objetivo é obter o dado de saldo de determinado cliente. 
Para isto, vamos considerar que este dado será recuperado da seguinte forma:

1 - Com o identificador do cliente, solicitamos ao serviço de dominio do cliente (consumer) o valor do saldo em conta. <br>
2 - Por sua vez, o consumer pergunta ao serviço de domínio da conta (provider) qual o saldo contido na conta atrelada aquele cliente. <br>
3 - Tendo a informação do saldo em conta, o consumer retorna a informação a quem a solicitou.

De forma resumida, temos os seguintes serviços:

* provider: mantém e gerencia informações relacionadas a contas bancárias.
* consumer: mantém e gerencia informações sobre clientes/correntistas.

A imagem abaixo representa esta interação que acabamos de definir.

<img src="../../../imgs/consumer_provider_findById_scenario_gRPC.png" alt="gRPC pact scenario"/>
 
## Como executar

1. Garanta que você tenha uma instância do Pact Broker rodando localmente. 
Para maiores informações, vide sessão [configuração do Pact Broker](../../../README.md#config-broker).

2. Abra os projetos contidos neste diretório (`consumer` e `provider`) em sua IDE de preferência e 
instale suas dependências.

3. Para gerar o contrato, basta rodar os testes Junit do projeto `consumer`. <br>
Obtendo sucesso, o plugin maven do Pact irá gerar um arquivo json contendo o contrato entre as APIs.
Veja os exemplos nas imagens abaixo.

<img src="../../../imgs/junit5-tests-runner.png" alt="Pact Runner Tests"/>

<img src="../../../imgs/pact-contract-generated.png" alt="Pact Contract Generated"/>

4. Com o contrato gerado, podemos publicá-lo no Pact Broker. 
Para isto, podemos utilizar o [plugin maven do Pact](https://mvnrepository.com/artifact/au.com.dius/pact-jvm-provider). <br>
É necessário confirmar que o plugin está configurado corretamente.

<img src="../../../imgs/pact-maven-plugin.png" alt="Pact Maven Plugin"/>

Após confirmar o status do plugin, abra outro terminal no diretório `consumer` e execute o seguinte comando para publicar o contrato no Broker:

```
mvn pact:publish
```

Em seguida, você poderá ver o contrato publicado no Pact Broker ```http://localhost:9292```.

<img src="../../../imgs/pact-published.png" alt="Pact publicado pelo consumer"/>

5. Com o contrato publicado no Broker, agora validaremos se a API provedora (provider) 
está aderente ao contrato.

No projeto `provider`, rode os testes Junit e verifique se eles são concluídos com sucesso. <br>
Este teste irá verificar no Broker os contratos disponiveis para validação, baixá-los e testá-los de acordo com a API provedora. <br>

> Para este passo, configuramos para que a publição do resultado seja feita automaticamente assim que a validação do contrato ocorre. <br>
> Veja a configuração em: [pom.xml](./provider/pom.xml) linha 117 <br>
```
<pact.verifier.publishResults>true</pact.verifier.publishResults>
```

Para verificar o resultado, basta acessar novamente o Broker. 

<img src="../../../imgs/pact-validated.png" alt="Pact validado pelo provider"/>
