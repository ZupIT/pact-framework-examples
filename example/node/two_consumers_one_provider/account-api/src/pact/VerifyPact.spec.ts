import { Verifier } from '@pact-foundation/pact';
import express from 'express';
import { Server } from 'http';
import { ACCOUNTS, APP_PORT, APP_URL, PACT_BROKER_URL } from '../constants';
import routes from '../routes';

describe('Pact verification', () => {
  let app: Server;

  beforeAll(async () => {
    app = express()
      .use(express.json())
      .use(routes)
      .listen(APP_PORT, () =>
        console.log(`AccountApi listening on port ${APP_PORT}`),
      );
  });

  afterAll(async () => {
    app.close();
  });

  it('checking if provider agrees with consumer', async () => {
    const account = ACCOUNTS.find(acc => acc.clientID === 1);

    const verify = new Verifier({
      providerBaseUrl: APP_URL,
      pactBrokerUrl: PACT_BROKER_URL,
      provider: 'AccountApi',
      publishVerificationResult: true,
      providerVersion: '1.0.0',
      stateHandlers: {
        'one client with your account': async () => account,
      },
    });

    await verify
      .verifyProvider()
      .then(() => console.log('Pact verification complete'))
      .catch(err => console.log(err));
  });
});
