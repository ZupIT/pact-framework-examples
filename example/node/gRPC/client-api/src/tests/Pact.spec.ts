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
  
  describe("Products API", () => {

    let productService: AccountService;
    let grpcMockServer: any;

    beforeAll(async () => {
      grpcMockServer = createMockServer({
        protoPath: "src/protos/AccountResource.proto",
        packageName: "br.com.zup.pact.provider.resource",
        serviceName: "AccountResource",
        rules: [
          { method: "findById", input: '.*', output: { accountId: 1, balance: 200.000 } },
          { method: "getAll", input: ".*", output: [ { accountId: 1, balance: 200.000, accountType: 'checking' } ] },
        ]
      });
      grpcMockServer.listen(GRPC_SERVER_URL);
    })

    beforeEach(() => {
      productService = new AccountService({
        interceptors: [ GRPC_HTTP_INTERCEPTOR ]
      });
    });

    beforeEach(() => {
      return provider.addInteraction({
        state: 'One account with id 15',
        uponReceiving: 'on AccountResource/findById',
        withRequest: {
          method: 'POST',
          headers: { 'Content-Type': 'application/json; charset=utf-8' },
          path: '/grpc/br.com.zup.pact.provider.resource.AccountResource/findById',
          body: {
            accountId: Matchers.integer()
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
    })

    // add expectations
    it("get Product by Id", () => {
      return productService.getById(1).then( response => {
        expect(response).toBeDefined();
      });
    })

    afterAll(() => {
      grpcMockServer.close(true);
    });
  })
})
