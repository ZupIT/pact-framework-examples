# Exemplo em Micronaut com gRPC 

Exemplo da criação de um Pact entre:
* 1 consumidor (shopping-cart-consumer)
* 1 provedor (product-provider)

- Veja este mesmo cenário feito em [Node](../../node/gRPC).

## Ferramentas

 - Kotlin - 1.4.10
 - OpenJdk 11
 - Micronaut - 2.1.2
 - Gradle - 6.7
 - Pact JVM
 - Pact Broker
 - Mockk
 - AssertJ
 


  ## Índice

<!--ts-->

- [Cenário](#Cenário)
- [Compatibilidade com gRPC](#Compatibilidade-com-gRPC)
  - [Solução no lado do Consumidor](#Solução-no-lado-do-Consumidor)
  - [Solução no lado do Provedor](#Solução-no-lado-do-Provedor)
  - [Mas se o gRPC já é uma espécie de contrato, porque preciso do PACT?](#Mas-se-o-gRPC-já-é-uma-espécie-de-contrato-porque-preciso-do-PACT?)
- [Como o PACT pode ajudar a evitar essas falhas?](#Como-o-PACT-pode-ajudar-a-evitar-essas-falhas?)
- [Como executar](#Como-executar)

<!--ts -->

## Cenário

Este exemplo aborda um cenário comum no setor de atacado/varejo.
Nosso objetivo é obter todos os produtos disponíveis no serviço provedor.
Para isto, vamos considerar que este dado será recuperado da seguinte forma:

1 - Solicitamos ao serviço de dominio do cliente (shopping-cart-consumer) a lista de produtos disponíveis, 
algo como um carrinho de compras que solicita a relação produtos em um serviço externo. <br>
2 - Por sua vez, o shopping-cart-consumer pergunta ao serviço de domínio dos produtos (product-provider) quais os produtos disponíveis e suas respectivas informações (nome, preço e quantidade). <br>
3 - De posse da relação de produtos o consumer as envia a quem solicitou.

De forma resumida, temos os seguintes serviços:

* product-provider: mantém e gerencia informações relacionadas a produtos.
* shopping-cart-consumer: solicita produtos ao provider atuando como um carrinho de compras.

A imagem abaixo representa esta interação que acabamos de definir:

<img src="../../../imgs/grpc-micronaut-scenario-general.png" alt="gRPC micronaut pact scenario"/>


## Compatibilidade com gRPC

Atualmente, o Pact suporta cenários de integração via REST, Mensageria e GraphQL. 
No entanto, como podemos ver no [roadmap do framework](https://pact.canny.io/feature-requests), o suporte oficial para gRPC já está planejado.
Enquanto esperamos este novo recurso, preparamos uma solução para que você possa iniciar seus testes desde já, de forma que seja fácil migrar para uma solução definitiva no futuro. 

Como dizemos acima, hoje o Pact não tem suporte para protocolo gRPC, mas e se conseguissemos 
transformar, ou representar, esta chamada em outro protocolo que o Pact entenda (ex: REST)? 
É exatamente esta a abordagem adotada aqui. 

* ### Solução no lado do Consumidor

Toda a comunicação feita entre um consumer e um provider gRPC é realizada através da criação de um **Channel**.
Logo, se conseguimos extender esta classe Channel e alterar seu comportamento, podemos ditar como a comunicação 
irá ocorrer. Extendendo o channel e sobrescrevendo o método *newCall*, conseguimos alterar seu comportamente para ( apenas em ambiente de teste ) realizar requisições REST no lugar de requisições gRPC. É assim que conseguimos obter informações destas chamadas gRPC, incluindo dados definidos no protofile como **package**, **service** e **method**, para então montarmos uma chamada REST, seguindo a seguinte convenção:

```
POST http://{{address}}/grpc/{{packageName}}.{{service}}/{{method}}
``` 
* Exemplo:
```
POST http://provider/grpc/br.com.zup.pact.product.provider.resource.ProductService/getAll
```

Tendo gerado então esta chamada REST, conseguimos seguir o fluxo de teste do Pact no lado do consumidor.

Para maiores detalhes sobre a implementação dos testes, vide o arquivo de teste [ShoppingCartContractTest.kt](./shopping-cart-consumer/src/test/kotlin/br/com/zup/pact/shopping/cart/consumer/pact/ShoppingCartContractTest.kt). 

* ### Solução no lado do Provedor

De forma análoga a solução proposta no lado do Consumidor, no lado do Provedor precisamos fazer a tradução entre a chamada gRPC e REST, mas de forma inversa (REST > gRPC). 
Para isto, precisamos criar um servidor REST como *proxy* (apenas em ambiente de teste), que escute as chamadas que definimos no lado do consumidor e as encaminhe para o servidor gRPC. 
Neste caso utilizamos *Micronaut Http Server* para implementarmos este *proxy* seguindo a convenção definida anteriormente.
Para maiores detalhes sobre a implementação dos testes, vide o arquivo de teste em [ShoppingCartProviderPactTest.kt](./product-provider/src/test/kotlin/br/com/zup/pact/product/provider/pact/ShoppingCartProviderPactTest.kt). 

A imagem a seguir representa esta solução:

<img src="../../../imgs/grpc-micronaut-scenario-create-pact.png" alt="Kotlin Pact gRPC solution"/>
 
## Mas se o gRPC já é uma espécie de contrato, porque preciso do PACT?

Como em muitos sistemas RPC, o gRPC é baseado na ideia de definir um serviço, especificando os métodos que podem ser chamados remotamente com seus parâmetros e tipos de retorno. No lado do servidor, o servidor implementa esse contrato (interface) e executa um servidor gRPC para lidar com as chamadas do cliente. Entretanto, devemos observar [pontos](https://docs.microsoft.com/pt-br/aspnet/core/grpc/versioning?view=aspnetcore-5.0) que podem levar a quebra de integrações, por exemplo:

- **Redefinir tipo de atributo** - ao alterar o tipo de um atributo, por exemplo, de `int32` para `double` no arquivo .proto, o provedor do serviço continuará funcionando, porém se o consumidor não receber essa alteração, haverá quebra na comunicação, esse tipo de alteração é identificado pelo PACT realizando o teste de contrato preventivamente.

- **Renomear um pacote, serviço ou método** - o gRPC usa o nome do pacote, o nome do serviço e o nome do método para criar a URL, caso algum desses itens sejam modificados sem a devida comunicação aos consumidores do serviço, haverá uma quebra de integração, esse tipo de alteração também é identificado pelo PACT realizando o teste de contrato preventivamente.

- **Removendo um serviço ou método** - haverá quebra de integração pois o cliente tentará acessar um recurso inexistente, esse tipo de alteração também é identificado pelo PACT realizando o teste de contrato preventivamente.

## Como o PACT pode ajudar a evitar essas falhas?

Quando o cliente(consumer) de uma aplicação gera um contrato com suas expectativas, dentre as informações ele pode definir o nome dos campos, seu tipo (linhas 26 à 38) e a url (ou path) com o pacote, nome do serviço e o método que deseja acessar (linhas 34 e 35) conforme imagem abaixo.

<img src="../../../imgs/pact-consumer-grpc-code.png" alt="code pact consumer class"/>
<br><br>

O contrato gerado fica registrado no [Pact Broker](https://docs.pact.io/pact_broker) onde o provedor(provider) do serviço pode baixá-lo para realizar as asserções. O contrato dentro do broker possui o seguinte formato:

<img src="../../../imgs/pact-contract-grpc-broker-view.png" alt="code pact consumer class"/>
<br><br>

Como podemos ver no recorte de contrato acima, ele trás consigo um exemplo fidedigno de tudo que o consumidor da aplicação espera que o provedor forneça, desta forma durante uma esteira de CI/CD o provedor conseguirá verificar se está totalmente de acordo com as expectativas do consumidor. O PACT garante uma **amarração** entre consumer e provider **sem a necessidade** de manter de pé ambientes com alto consumo de recursos (dev, homologação) e sem os riscos de tais ambientes ou seus mocks estarem **desatualizados** o que geraria sinais falsos de sucesso nos testes.

## Como executar

1. Garanta que você tenha uma instância do Pact Broker rodando localmente. 
Para maiores informações, vide seção [configuração do Pact Broker](../../../README.md#config-broker).

2. Abra os projetos contidos neste diretório (`consumer` e `provider`) em sua IDE de preferência e 
instale suas dependências. Para garantir que os assets gRPC foram gerados corretamente, 
rode o seguinte comando na raiz de cada projeto:

```
gradle build
```

3. Para gerar o contrato, basta rodar os testes Junit da classe 
`ShoppingCartContractTest` no projeto `consumer`. <br>
Obtendo sucesso, o plugin Gradle do Pact irá gerar um arquivo json contendo o contrato entre as APIs.
Veja os exemplos nas imagens abaixo.

<img src="../../../imgs/grpc-micronaut-terminal-run-tests.png" alt="Pact Runner Tests"/>

<img src="../../../imgs/grpc-micronaut-pact-folder.png" alt="Pact Contract Generated"/>

4. Com o contrato gerado, podemos publicá-lo no Pact Broker. 
Para isto, podemos utilizar o plugin Gradle do Pact. <br>
É necessário confirmar que o plugin está configurado corretamente dentro do arquivo 
`build.gradle`:

<img src="../../../imgs/grpc-micronaut-pact-publish-plugin.png" alt="Pact Gradle Plugin"/>

Após confirmar o status do plugin, abra outro terminal no diretório `consumer` e execute o seguinte comando para publicar o contrato no Broker:

```
gradle pactPublish
```

Em seguida, você poderá ver o contrato publicado no Pact Broker [http://localhost:9292](http://localhost:9292).

<img src="../../../imgs/grpc-micronaut-pact-broker.png" alt="Pact publicado pelo consumer"/>

5. Com o contrato publicado no Broker, agora validaremos se a API provedora (provider) 
está aderente ao contrato.

No projeto `provider`, rode os testes da classe [ShoppingCartProviderPactTest.kt](./provider/src/test/kotlin/br/com/zup/pact/provider/pact/ShoppingCartProviderPactTest.kt) e verifique se eles são concluídos com sucesso. <br>

Este teste irá verificar no Broker os contratos disponiveis para validação, baixá-los e testá-los de acordo com a API provedora. <br>

> Para este passo, configuramos para que a publição do resultado seja feita automaticamente assim que a validação do contrato ocorre. <br>

Para verificar o resultado, basta acessar novamente o Broker. 

<img src="../../../imgs/grpc-micronaut-pact-broker-with-result.png" alt="Pact validado pelo provider"/>
