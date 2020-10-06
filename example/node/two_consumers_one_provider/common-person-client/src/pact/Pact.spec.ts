import { Matchers, Pact } from '@pact-foundation/pact';
import axios from 'axios';
import path from 'path';

describe('Pact', () => {
  const provider = new Pact({
    port: 1234,
    log: path.resolve(process.cwd(), 'logs', 'pact.log'),
    dir: path.resolve(process.cwd(), 'pacts'),
    provider: 'AccountApi',
    consumer: 'CommonPersonClient',
  });

  describe('Given an existing ID', () => {
    beforeAll(() =>
      provider.setup().then(() => {
        provider.addInteraction({
          state: 'one client with your account',
          uponReceiving: 'a request for account',
          withRequest: {
            method: 'GET',
            path: '/balance/1',
          },
          willRespondWith: {
            status: 200,
            headers: { 'Content-Type': 'application/json; charset=utf-8' },
            body: {
              clientID: Matchers.decimal(),
              balance: Matchers.decimal(),
              name: Matchers.string(),
            },
          },
        });
      }),
    );

    it('get balance by client ID', async () => {
      await axios.get(`${provider.mockService.baseUrl}/balance/1`);
    });

    afterEach(() => provider.verify());
  });

  afterAll(() => provider.finalize());
});
