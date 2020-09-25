import { GRPC_SERVER_URL } from './../constants';
import { Server, ServerCredentials } from '@grpc/grpc-js';
import { Verifier } from '@pact-foundation/pact';
import express from 'express';
import { Server as HttpServer } from 'http';
import { APP_PORT, APP_URL, PACT_BROKER_URL } from '../constants';
import { AccountController } from '../controllers/account.controller';
import { loadProtoFile } from '../proto-loader';
import routes from './grpc-routes';

async function createGrpcServer(): Promise<Server>  {
  const accountController = new AccountController();
  const grpcServer = new Server();

  var protofile: any = loadProtoFile(__dirname + '/../../AccountResource.proto');
  grpcServer.addService(protofile.br.com.zup.pact.provider.resource.AccountResource.service, { 
      getBalanceByClientId: accountController.findById,
      getAll: accountController.getAll
  });

  return new Promise( (resolve, reject) => {
    grpcServer.bindAsync(GRPC_SERVER_URL, ServerCredentials.createInsecure(), err => {

      if (err) reject(err);

      grpcServer.start();
      resolve(grpcServer);
    });
  })
}

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
        'default state': async () => {}
      }
    });
    await verify
      .verifyProvider()
      .then(() => console.log('Pact verification complete'))
      .catch(err => console.log(err))
      .finally(() => app.close());
  });

  afterAll( () => {
    grpcServer.tryShutdown((err) => {console.log()});
    app.close();
  })
});
