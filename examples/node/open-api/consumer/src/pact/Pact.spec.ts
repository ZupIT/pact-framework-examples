import { AccountService } from './../services/AccountService';
import { pactWith } from 'jest-pact';
import { Matchers, Pact } from '@pact-foundation/pact';
import axios from 'axios';

pactWith({ port: 3333, consumer: "ClientApi", provider: "AccountApi" }, (provider: Pact) => {
   
  describe('Find Account By Id', () => {
    let accountService: AccountService;

    beforeAll(async () => {
      accountService = new AccountService();
      return await provider.addInteraction({
        state: 'default state',
        uponReceiving: 'a request for account',
        withRequest: {
          method: 'GET',
          path: '/account/1',
        },
        willRespondWith: {
          status: 200,
          headers: { 'Content-Type': 'application/json; charset=utf-8' },
          body: {
            id: Matchers.integer(),
            balance: Matchers.decimal(),
            accountType: Matchers.string()
          },
        },
      })
    });

    it('get balance by client ID', async () => {
      await accountService.getBalanceByClientID(1);
    });

    afterEach(() => provider.verify());
  });

  describe('Get an inexisting account', () => {
    let accountService: AccountService;

    beforeAll(async () => {
      accountService = new AccountService();
      return await provider.addInteraction({
        state: 'no accounts',
        uponReceiving: 'a request for an inexisting account',
        withRequest: {
          method: 'GET',
          path: '/account/15',
        },
        willRespondWith: {
          status: 404
        },
      });
    });

    it('get balance by client ID', async () => {
      await accountService.getBalanceByClientID(15).catch( error => {
        expect(error).toBeDefined();
      });
    });

    afterEach(() => provider.verify());
  });

  describe('List all accounts', () => {
    beforeAll(async () =>
        await provider.addInteraction({
          state: 'default state',
          uponReceiving: 'a request for all accounts',
          withRequest: {
            method: 'GET',
            path: '/account',
          },
          willRespondWith: {
            status: 200,
            headers: { 'Content-Type': 'application/json; charset=utf-8' },
            body: Matchers.eachLike({
              id: Matchers.integer(),
              balance: Matchers.decimal(),
              accountType: Matchers.string(),
            }),
          },
        })
    );

    it('get balance by client ID', async () => {
      await axios.get(`${provider.mockService.baseUrl}/account`).then();
    });

    afterEach(() => provider.verify());
  });

})

