# Exemplo em Java Spring Boot com OpenAPI

Exemplo da criação de um Pact entre:
* 1 consumidor (Consumer)
* 1 provedor (Provider)

## Ferramentas

 - OpenJdk 11
 - Spring Boot
 - [Swagger Mock Validator](https://bitbucket.org/atlassian/swagger-mock-validator/src/master/)
 - Swagger
 - Lombok
 - Pact JVM
 - Pact Broker
 
## Cenário

Este exemplo aborda um cenário comum no setor bancário.
Nosso objetivo é obter o saldo de determinado cliente.
Para isto, vamos considerar que este dado será recuperado da seguinte forma:

1 - Com o identificador do cliente, solicitamos ao serviço de dominio do cliente (consumer) o valor do saldo em conta. <br>
2 - Por sua vez, o consumer pergunta ao serviço de domínio da conta (provider) qual o saldo contido na conta atrelada àquele cliente. <br>
3 - Tendo a informação do saldo em conta, o consumer retorna a informação a quem a solicitou.

A imagem abaixo representa esse fluxo.

<img src="../../../../imgs/consumer_provider_findById_scenario.png" alt="Get Account By Id"/>

## Como executar

1. Garanta que você tenha uma instância do Pact Broker rodando localmente. 
Para maiores informações, vide sessão [configuração do Pact Broker](../../../../README.md#config-broker).

2. Abra os projetos contido neste diretório (`consumer` e `provider`) em sua IDE de preferência e 
instale suas dependências. Se estiver usando o IntelliJ, você também precisará instalar o [Plugin do Lombok](https://projectlombok.org/setup/intellij).

## Gerando o contrato no Consumer
1. Para gerar o contrato, basta rodar os testes Junit do projeto `consumer`. <br>
Obtendo sucesso, o plugin maven do Pact irá gerar um arquivo json contendo o contrato entre as APIs.
Veja os exemplos nas imagens abaixo.

<img src="../../../../imgs/junit5-tests-runner.png" alt="Pact Runner Tests"/>

<img src="../../../../imgs/pact-contract-generated.png" alt="Pact Contract Generated"/>

2. Com o contrato gerado, podemos publicá-lo no Pact Broker. 
Para isto, podemos utilizar o plugin maven do Pact. <br>
É necessário confirmar que o plugin está configurado corretamente.

<img src="../../../../imgs/pact-maven-plugin.png" alt="Pact Maven Plugin"/>

Após confirmar o status do plugin, abra outro terminal no diretório `consumer` e execute o seguinte comando para publicar o contrato no Broker:

```
mvn pact:publish
```

Em seguida, você poderá ver o contrato publicado no Pact Broker [http://localhost:9292](http://localhost:9292).

<img src="../../../../imgs/pact-published.png" alt="Pact publicado pelo consumer"/>

3. Com o contrato publicado no Broker, agora iremos verificar se está em conformidade com a especificação OpenApi do provider.

## Gerando o arquivo JSON com a especificação OpenAPI do Provider

Dentre as formas de verificação de contrato disponibilizadas na [documentação](https://bitbucket.org/atlassian/swagger-mock-validator/src/master/) do Swagger Mock Validator, utilizaremos a que um arquivo JSON com a especificação OpenAPI é fornecido para o Consumer validar suas expectativas. Primeiramente devemos adicionar o plugin `springdoc-openapi-maven-plugin` no pom.xml do provider para gerarmos o arquivo JSON:
```
<build>
        <plugins>
            <plugin>
                <groupId>org.springdoc</groupId>
                <artifactId>springdoc-openapi-maven-plugin</artifactId>
                <version>1.1</version>
                <executions>
                    <execution>
                        <id>contract-test</id>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <apiDocsUrl>http://${open-api-url}</apiDocsUrl>
                    <outputFileName>contract-openapi.json</outputFileName>
                    <outputDir>${project.basedir}/target/openapi</outputDir>
                    <skip>false</skip>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
Na tag `configuration` adicionamos a url onde está sendo disponibilizada a documentação OpenAPI do provider,o nome que será dado ao arquivo gerado e por último o caminho da pasta de destino.
Feito isso, execute o projeto Provider e em seguida abra o terminal e utilize o comando abaixo para gerar o arquivo JSON:
```
mvn springdoc-openapi:generate
```
Após a execução vá até a pasta target/openapi e verifique se dentro dela há um arquivo contract-openapi.json e em caso positivo siga para o próximo passo.

## Verificando o contrato utilizando o Swagger Mock Validator

A Atlassian disponibilizou uma ferramenta que podemos utilizar para testar contratos do Pact onde o Provider, ao invés de implementar os testes de contrato, disponibiliza a especificação OpenApi dos seus serviços e assim podemos fazer a asserção junto ao contrato disponibilizado pelo Consumer. Para que isso seja possível, instale a dependência do Swagger Mock Validator utilizando o comando npm abaixo.
Para mais informações consulte a [documentação oficial.](https://bitbucket.org/atlassian/swagger-mock-validator/src/master/)

```
npm install --global swagger-mock-validator
```
Após a instalação, abra um terminal na raiz do diretório do projeto consumer. De acordo com a documentação do Swagger Mock Validator, o comando a ser executado pode seguir, dentre outras possibilidades, o seguinte padrão:

```
swagger-mock-validator /path/to/swagger.json https://pact-broker.com --provider my-provider-name --tag production
```
O primeiro parâmetro que o comando recebe é o path onde está disponível o arquivo JSON que geramos pelo springdoc-openapi, que neste caso é `provider/target/openapi/contract-openapi.json`. O segundo parâmetro é o endereço do Pact Broker, que neste projeto está disponibilizado via Docker no endereço `http://localhost:9292`. Logo após a flag `--provider` indicamos o nome do Provider que está registrado junto ao contrato no Pact Broker e por último com a `--tag` podemos informar a tag do contrato como `production, developer...` e neste exemplo é `master`. Feitas essas observações, executaremos o seguinte comando na **raiz do projeto consumer** e caso você tenha aberto o terminal em outro local atualize o path do comando conforme necessário.  

```
swagger-mock-validator ../provider/target/openapi/contract-openapi.json http://localhost:9292 --provider AccountBalanceProvider --tag master
```
Feito isso, o terminal retornará a seguinte mensagem:

```
0 error(s)
0 warning(s)
```
A saída acima indica que o contrato está em conformidade com a especificação OpenApi fornecida pelo provider.