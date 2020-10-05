"use strict";

import { GRPC_SERVER_URL } from './../constants';
import { InterceptorOptions } from '@grpc/grpc-js';
import { InterceptingCall, NextCall } from '@grpc/grpc-js/build/src/client-interceptors';
import { Pact } from '@pact-foundation/pact';
import { HtttpRequester } from '../adapters/HttpRequester';
import { AccountService } from '../services/account.service';
import { pactWith } from 'jest-pact';
import { Matchers } from  '@pact-foundation/pact';
const { createMockServer } = require("grpc-mock");

const MOCK_SERVER_BASE_URL = 'http://localhost:1234';

const GRPC_HTTP_INTERCEPTOR = (options: InterceptorOptions, nextCall: NextCall): InterceptingCall => {
  return new InterceptingCall(nextCall(options), new HtttpRequester(MOCK_SERVER_BASE_URL, options));
}

pactWith({ port: 1234, consumer: "ClientApi", provider: "AccountApi" }, (provider: Pact) => {
  
  describe("Account API", () => {

    let productService: AccountService;
    let grpcMockServer: any;

    beforeAll(async () => {
      grpcMockServer = createMockServer({
        protoPath: "src/protos/AccountResource.proto",
        packageName: "br.com.zup.pact.provider.resource",
        serviceName: "AccountResource",
        rules: [
          { method: "findById", input: '.*', output: { accountId: 15, balance: 200.00 } },
          { method: "getAll", input: ".*", output: [ { accountId: 15, balance: 200.00, accountType: 'checking' } ] },
        ]
      });
      grpcMockServer.listen(GRPC_SERVER_URL);
      await provider.removeInteractions();
    });

    beforeEach(() => {
      productService = new AccountService({
        interceptors: [ GRPC_HTTP_INTERCEPTOR ]
      });
    });

    // Verify mock service
    afterEach(async () => provider.verify());

    // Create contract
    afterAll(async () => provider.finalize());

    describe('should get account by Id', () => {
      beforeAll( async() => {
        await provider.addInteraction({
          state: 'there is one account with id 15',
          uponReceiving: 'findById call of account 15',
          withRequest: {
            method: 'POST',
            headers: { 'Content-Type': 'application/json; charset=utf-8' },
            path: '/grpc/br.com.zup.pact.provider.resource.AccountResource/findById',
            body: {
              accountId: Matchers.integer(15)
            }
          },
          willRespondWith: {
            status: 200,
            headers: { 'Content-Type': 'application/json; charset=utf-8' },
            body: {
              accountId: Matchers.integer(),
              balance: Matchers.decimal()
            },
          },
        });
      });
      
      // add expectations
      it("get Product by Id", async () => {
        await productService.getById(15).then();
      })
    })

    describe('Get account Error', () => {
      
      beforeAll(async () => {
        await provider.addInteraction({
          state: 'inexistent account',
          uponReceiving: 'findById call of innexistent account',
          withRequest: {
            method: 'POST',
            headers: { 'Content-Type': 'application/json; charset=utf-8' },
            path: '/grpc/br.com.zup.pact.provider.resource.AccountResource/findById',
            body: {
              accountId: Matchers.integer()
            }
          },
          willRespondWith: {
            status: 500,
            headers: { 'Content-Type': 'application/json; charset=utf-8' },
            body: { details: Matchers.string() }
          },
        });
      })
      
      // add expectations
      it("get Product by Id", async () => {
        await productService.getById(1).then();
      })

    });

    afterAll(() => {
      grpcMockServer.close(true);
    });
  })
})
