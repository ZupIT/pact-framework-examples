import { Pact } from '@pact-foundation/pact';
import axios, { AxiosResponse } from 'axios';
import path from 'path';

describe('Pact with Account', () => {
  const provider = new Pact({
    port: 1234,
    log: path.resolve(process.cwd(), 'logs', 'pact.log'),
    dir: path.resolve(process.cwd(), 'pacts'),
    provider: 'AccountApi',
    consumer: 'ClientApi',
  });

  const expectedBody = {
    clientID: 1,
    accountID: 10,
    balance: 100,
  };

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
            body: expectedBody,
          },
        });
      }),
    );

    it('get balance by client ID', async () => {
      const response: AxiosResponse = await axios.get(
        `${provider.mockService.baseUrl}/balance/1`,
      );
      const clientInfo = response.data;
      expect(clientInfo).toHaveProperty('clientID');
      expect(clientInfo).toHaveProperty('accountID');
      expect(clientInfo).toHaveProperty('balance');
    });

    afterEach(() => provider.verify());
  });

  afterAll(() => provider.finalize());
});

describe('Pact with PrimeAccountDetails', () => {
  const provider = new Pact({
    port: 4321,
    log: path.resolve(process.cwd(), 'logs', 'pact.log'),
    dir: path.resolve(process.cwd(), 'pacts'),
    provider: 'PrimeAccountDetailsApi',
    consumer: 'ClientApi',
  });

  const expectedBody = {
    isPrime: true,
    discountPercentageFee: 2,
  };

  describe('Given an existing ID', () => {
    beforeAll(() =>
      provider.setup().then(() => {
        provider.addInteraction({
          state: 'one client with your account',
          uponReceiving: 'a request for account',
          withRequest: {
            method: 'GET',
            path: '/prime/1',
          },
          willRespondWith: {
            status: 200,
            headers: { 'Content-Type': 'application/json; charset=utf-8' },
            body: expectedBody,
          },
        });
      }),
    );

    it('get balance by client ID', async () => {
      const response: AxiosResponse = await axios.get(
        `${provider.mockService.baseUrl}/prime/1`,
      );
      const clientInfo = response.data;
      expect(clientInfo).toHaveProperty('isPrime');
      expect(clientInfo).toHaveProperty('discountPercentageFee');
    });

    afterEach(() => provider.verify());
  });

  afterAll(() => provider.finalize());
});
