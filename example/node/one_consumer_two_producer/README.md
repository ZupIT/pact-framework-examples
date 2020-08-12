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
  git clone https://github.com/martetech/dtp-pact-examples
```

2. Go to directories `account-api`, `client-api` and `prime-account-details-api` and execute `yarn install` (or `npm install`) to install necessary packages for each of them.

3. Go to directory `infrastructure` with `docker-compose.yml` for up Postgres and Pact Broker containers.

```shell
  docker-compose up
```

4. To start with tests, we need to generate the PACT for our consumer.
Go to `client-api` directory and execute `yarn test ; yarn pact:publish` to generate Pact Contract and publish it on Pact Broker. To view this result, go to `http://localhost`, you should see something like this:

<img src="../../../imgs/new-pact-contract.png" alt="new pact contract"/>

You can checkout the Pact contract JSON file at `client-api/pacts`.

5. And to validate the generated contract, go to `account-api` and execute `yarn test`, this will run verifier Pact contract and publish the result on Pact Broker. To see, go again to `http://localhost` and you should see something like this:

<img src="../../../imgs/validated-pact-contract.png" alt="new pact contract"/>

  **Do not forget to hit ctrl + c to end the test REPL and release it's port.**

6. To test the PACT against the second producer, execute the same process described in the last time, but for `prime-account-details-api` this time.

## Links

- How to Pact works: https://docs.pact.io/how-pact-works#slide-1
- Pact Documentation: https://docs.pact.io/
- Pact Flow: https://pactflow.io/
- Pact Broker: https://github.com/pact-foundation/pact_broker
- POC with Java: https://github.com/vinirib/zup-pact-workshop
