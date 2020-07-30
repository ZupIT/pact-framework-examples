# PACT - Node

A simple Node project implementing Pact to integration tests.

## Tools

- Express
- Typescript
- Jest
- Pact

## Pact Concept

Pact is a code-first tool for testing HTTP and message integrations using contract tests. Contract tests assert that inter-application messages conform to a shared understanding that is documented in a contract. Without contract testing, the only way to ensure that applications will work correctly together is by using expensive and brittle integration tests.

Do you set your house on fire to test your smoke alarm? No, you test the contract it holds with your ears by using the testing button. Pact provides that testing button for your code, allowing you to safely confirm that your applications will work together without having to deploy the world first.

## Getting started

1. Clone the project on your machine.

```shell
  git clone https://github.com/iannsantos/pact-node
```

2. Go to directories `account-api` and `client-api` and execute `yarn install` (or `npm install`) to install necessary packages.
3. Go to directory `infrastructure` with `docker-compose.yml` for up Postgres and Pact Broker containers.

```shell
  docker-compose up
```

4. To start with tests, go to `client-api` directory and execute `yarn test ; yarn pact:publish` to generate Pact Contract and publish it on Pact Broker. To view this result, go to `http://localhost`, you should see something like this:

<img src="images/new-pact-contract.png" alt="new pact contract"/>

Also you'll see the Pact contract JSON file on `client-api/pacts`.

5. And to validate the generated contract, go to `account-api` and execute `yarn test`, this will run verifier Pact contract and publish the result on Pact Broker. To see, go again to `http://localhost` and you should see something like this:

<img src="images/validated-pact-contract.png" alt="new pact contract"/>

## Links

- How to Pact works: https://docs.pact.io/how-pact-works#slide-1
- Pact Documentation: https://docs.pact.io/
- Pact Flow: https://pactflow.io/
- Pact Broker: https://github.com/pact-foundation/pact_broker
- POC with Java: https://github.com/vinirib/zup-pact-workshop
