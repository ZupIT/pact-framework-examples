# Exemplo de Integração Contínua com GitlabCI

Neste exemplo, temos uma implementação do Pact com Micronaut + GRPC + Pact + GitlabCI
* 1 consumidor (pact-consumer-sample)
* 1 provedor (pact-provider-sample)

## Ferramentas

 - GitlabCI
 - Postgresql
 - Pact Broker
 - Kotlin
 - Micronaut


Índice
=================

<!--ts-->
   * [Caracteristicas deste exemplo](#Caracteristicas-deste-exemplo)
   * [Cenários contidos neste exemplo](#Cenários-contidos-neste-exemplo)
      * [Primeiro cenário](#Primeiro-cenário)
      * [Segundo cenário](#Segundo-cenário)
      * [Terceiro cenário](#Terceiro-cenário)
   * [Como executar](#Como-executar)
   * [Observações](#Observações)
<!--ts -->

## Caracteristicas deste exemplo

Para termos melhor proveito do teste de contrato com o Pact, devemos automatizar suas verificações com ferramentas CI. Neste exemplo, o GitlabCI será utilizado para checar o contrato entre as integrações. Com isso, conseguimos garantir as pontas consumer e provider perante uma integração que é realizada, como isso ajuda em um cenário real? Quando utilizamos o teste de contrato do Pact, conseguimos assegurar que o contrato de integração é respeitado quanto por quem provê, quanto por que consome, garantindo a integridade não só do contrato, mas da regra de negócio que é utilizada com os dados vindos de uma chamada de integração. Quando incluímos o Pact nessa verificação em um fluxo automatizado de CI, temos maior velocidade de verificação dos nossos contratos.

Um fluxo de CI de teste de contrato segue os seguintes passos.

Quando o Consumer inicia o CI:
   - Consumer realiza os steps de CI até chegar no teste de contrato.
   - Nos testes de contrato, é executado o teste unitário do Pact no projeto Consumer, caso o teste tenha sido executado com sucesso, o arquivo contrato do Pact é gerado na pasta target do projeto.
   - No próximo passo utilizamos o gradle para enviar esse contrato para o Broker do Pact com o comando gradle pactPublish.
   - Será enviado um comando trigger para inicializar o CI do provider da integração.
   - O CI do Provider continuará o processo, rodando a classe de teste unitário do Pact implementada no Provider.
   - Essa classe, baixará o contrato do Broker, executará o teste subindo seu endpoint e executando o teste baseando se na expectativa que o consumer descreveu nos testes.
   - Caso o teste seja efetuado com sucesso, significa que o contrato é válido entre as integrações, o CI retornará OK e temos nossa garantia nessa integração.

Quando o Provider inicia o CI:
   - Provider realiza os steps de CI até chegar no teste de contrato.
   - O Provider executará a classe de testes de contrato que baixará a ultima versão de contrato publicada no Broker. Se essa versão condizer com o endpoint que o provider está fornecendo, o teste passará, se houve alguma alteração inesperada pelo lado do consumer, o teste falhará e o CI falhará, indicando que há algo errado na integração.

## Como executar

Toda Stack desse exemplo foi testado apenas em ambiente linux.

1. Suba os containers que estão no arquivo docker-compose.yml

```
sudo docker-compose up
```

Para que consigamos executar os pipelines localmente precisamos criar Runners do Gitlab, neste exemplo incluí dois Runners que são Runners Docker, eles são responsáveis por rodar os Pipelines do GitlabCI.

2. Com todos containers executando, execute o script (construct-pact-gitlab.sh)

```
sudo chmod +x construct-pact-gitlab.sh
./construct-pact-gitlab.sh
```
Neste passo, serão linkados os Runners no GitlabCI, será criado um token para chamadas API no Gitlab e serão criados todos os requisitos dos dois repositórios para que seja executada a pipeline. Também serão clonados os repos criados e copiados os dois projetos exemplo para esses repos e enviados para o container Gitlab que quando receber os arquivos executará a pipeline.


## Observações

No JenkinsFile, quando o consumidor e o provedor validam seus contratos, é uma boa prática versionar as APIs com um hashcode.
No nosso caso utilizamos a hash do git commit, além de tags, para visualizarmos versões mais recentes facilmente. Esta prática também pode ser encontrada na [documentação do pact](https://docs.pact.io/getting_started/versioning_in_the_pact_broker).
