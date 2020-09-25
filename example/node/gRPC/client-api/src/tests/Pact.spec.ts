"use strict";

import { InterceptorOptions } from '@grpc/grpc-js';
import { InterceptingCall, NextCall } from '@grpc/grpc-js/build/src/client-interceptors';
import { Pact } from '@pact-foundation/pact';
import { HtttpRequester } from '../adapters/HttpRequester';
import { ProductService } from '../services/product.service';
import { pactWith } from 'jest-pact';
import { Matchers } from  '@pact-foundation/pact';

const MOCK_SERVER_BASE_URL = 'http://localhost:1234';

const GRPC_HTTP_INTERCEPTOR = (options: InterceptorOptions, nextCall: NextCall): InterceptingCall => {
  return new InterceptingCall(nextCall(options), new HtttpRequester(MOCK_SERVER_BASE_URL, options));
}


pactWith({ port: 1234, consumer: "ClientApi", provider: "AccountApi" }, (provider: Pact) => {
  
  describe("Products API", () => {

    let productService: ProductService;

    beforeEach(() => {
      productService = new ProductService({
        interceptors: [ GRPC_HTTP_INTERCEPTOR ]
      });
    });

    beforeEach(() => {
      return provider.addInteraction({
        state: 'default state',
        uponReceiving: 'on ProductEndPoint/findById',
        withRequest: {
          method: 'POST',
          path: '/grpc/grpcproduct.ProductEndPoint/findById',
          body: {
            value: Matchers.string()
          }
        },
        willRespondWith: {
          status: 200,
          headers: { 'Content-Type': 'application/json; charset=utf-8' },
          body: {
            id: Matchers.integer(),
            name: Matchers.string()
          },
        },
      });
    })

    // add expectations
    it("get Product by Id", () => {
      return productService.findById('1').then( response => {
        expect(response).toBeDefined();
      });
    })
  })
})
