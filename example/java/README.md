<img src="../../imgs/pact-logo.png" alt="Pact Logo"/>

This repository contains an example of implementation of Pact Framework in Spring Boot and Junit 5.
This folder contains examples of implementation of Pact Framework in Spring Boot and Junit 5.

It has these examples:
 - messaging-kafka: implementation of using Kafka;
 - one_consumer_one_producer: implemantation with one consumer and one producer;
 - one_consumer_two_producer: implemantation with one consumer and two producers;
 - two_consumer_one_producer: implemantation with two consumers and one producer.

## Tools Used

 - OpenJdk 11
 - Spring Boot
 - Swagger
 - Pact JVM
 - Pact Broker
 
## How to run

Each folder we have one or more consumer and producer. Open the projects on your favorite IDE.

#### Generating Contract

To generate contract, in the consumer project(s) run the junit Tests. After passed tests, the pact maven plugin will generate a json file from contract between client and account api.

<img src="../../imgs/junit5-tests-runner.png" alt="Pact Runner Tests"/>

<img src="../../imgs/pact-contract-generated.png" alt="Pact Contract Generated"/>

#### Publish Contract on Pact Broker

To publish this contract to Pact Broker, open terminal on folder "infraestructure" and up Pact Broker Container.

```
docker-compose up -d
```

The Pact broker will be avaliable on http://localhost

To make the consumer send contract to broker, we must have pact maven plugin configured corretly, as the same of above.

<img src="../../imgs/pact-maven-plugin.png" alt="Pact Maven Plugin"/>

After confirm this information, open another terminal from the consumer root folder and run maven goal pact publish.

```
mvn pact:publish
```

After successful published, you can see contract on pact broker url.


#### Test Contract on Provider side (Running the producer)

On the project provider project(s) we have some tests based on the consumer contract.  You can check if its works running junit tests. Make sure your pact Broker was up when you run the tests. The concept is that the provider will obtain the contract from the boker and do tests with mocks. If your contract is ok the junit tests will be passed. You can see if the contract was successfully passed or it has broked on pact broker url.
