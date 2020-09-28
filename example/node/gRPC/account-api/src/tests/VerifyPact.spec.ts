import { Server } from '@grpc/grpc-js';
import { Verifier } from '@pact-foundation/pact';
import express from 'express';
import { Server as HttpServer } from 'http';
import { APP_PORT, APP_URL, PACT_BROKER_URL } from '../constants';
import { createGrpcServer } from '../gRPCServer';
import accountRepository from '../repositories/account/account.repository';
import routes from './grpc-routes';
 
async function createHttpServer(): Promise<HttpServer> {
  const expressServer = express()
  .use(express.json())
  .use(routes);

  return new Promise( (resolve) => {
    expressServer
    .listen(APP_PORT, () =>
      resolve()
    );
  });
}

describe('Pact verification', () => {
  let grpcServer: Server;
  let app: HttpServer;

  beforeAll(async () => {
    grpcServer = await createGrpcServer();
    app = await createHttpServer();
  });

  it('checking if provider agrees with consumer', async () => {
    const verify = new Verifier({
      providerBaseUrl: APP_URL,
      pactBrokerUrl: PACT_BROKER_URL,
      provider: 'AccountApi',
      publishVerificationResult: true,
      providerVersion: '1.0.0',
      stateHandlers: {
        'default state': async () => {
          accountRepository.setAccounts(new Map([
              ['1', { id: 1 , balance: 200.00, accountType: 'checking' }]
            ])
          );
        },
        'inexistent account': async () => {
          accountRepository.setAccounts(new Map([]));
        }
      }
    });
    await verify
      .verifyProvider()
      .then(() => console.log('Pact verification complete'))
      .catch(err => console.log(err));
  });

  afterAll( () => {
    grpcServer.tryShutdown((err) => err ? console.log(err) : '' );
    app.close();
  })
});
