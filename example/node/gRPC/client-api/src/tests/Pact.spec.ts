import { ClientOptions, InterceptorOptions } from '@grpc/grpc-js';
import { InterceptingCall, NextCall } from '@grpc/grpc-js/build/src/client-interceptors';
import { Matchers, Pact } from '@pact-foundation/pact';
import path from 'path';
import { findProductById } from '../services/product.service';
import { HtttpRequester } from './../adapters/HttpRequester';

const MOCK_SERVER_BASE_URL = 'http://localhost:1234'

const GRPC_HTTP_INTERCEPTOR = (options: InterceptorOptions, nextCall: NextCall): InterceptingCall => {
  return new InterceptingCall(nextCall(options), new HtttpRequester(MOCK_SERVER_BASE_URL, options));
}

describe('PACT', () => {

  const provider = new Pact({
    port: 1234,
    log: path.resolve(process.cwd(), 'logs', 'pact.log'),
    dir: path.resolve(process.cwd(), 'pacts'),
    provider: 'AccountApi',
    consumer: 'ClientApi',
  });

  describe('Given an existing ID', () => {
  
    beforeAll(() =>
      provider.setup().then(() => {
        provider.addInteraction({
          state: 'default state',
          uponReceiving: 'sample.product.ProductEndPoint/findById',
          withRequest: {
            method: 'GET',
            path: '/grpc/grpcproduct.ProductEndPoint/findById/1',
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
      }),
    );
  
    it('get product by ID', async () => {
      const opts: ClientOptions = {
        interceptors: [
          GRPC_HTTP_INTERCEPTOR
        ],
        // WIP: Channel override approach
        // channelOverride: new GrpcHttpChannel('hhtp://localhost:50051', credentials.createInsecure(), {})
      };
      
      const response = await findProductById(1, opts);
      expect(response).not.toBeNull();
    });
  
    afterEach(() => provider.verify());
  });
  afterAll(() => provider.finalize());
});
