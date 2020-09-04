import { Verifier } from '@pact-foundation/pact';
import express from 'express';
import { Server } from 'http';
import { APP_PORT, APP_URL, PACT_BROKER_URL, accountMocked } from '../constants';
import routes from '../routes';

describe('Pact verification', () => {
  let app: Server;

  beforeAll(async () => {
    app = express()
      .use(express.json())
      .use(routes)
      .listen(APP_PORT, () =>
        console.log(`Provider listening on port ${APP_PORT}`),
      );
  });

  it('checking if provider agrees with consumer', async () => {
    const verify = new Verifier({
      providerBaseUrl: APP_URL,
      pactBrokerUrl: PACT_BROKER_URL,
      provider: 'AccountApi',
      publishVerificationResult: true,
      providerVersion: '1.0.0',
      stateHandlers: {
        'one client with your account': async () => accountMocked
      }
    });
    await verify
      .verifyProvider()
      .then(() => console.log('Pact verification complete'))
      .catch(err => console.log(err))
      .finally(() => app.close());
  });
});
