import { Verifier } from '@pact-foundation/pact';
import { APP_URL, PACT_BROKER_URL } from '../constants';

describe('Pact verification', () => {
  it('checking if provider agrees with consumer', async () => {
    const verify = new Verifier({
      providerBaseUrl: APP_URL,
      pactBrokerUrl: PACT_BROKER_URL,
      provider: 'AccountApi',
      publishVerificationResult: true,
      providerVersion: '1.0.0',
    });

    await verify
      .verifyProvider()
      .then(() => console.log('Pact verification complete'))
      .catch(err => console.log(err));
  });
});
