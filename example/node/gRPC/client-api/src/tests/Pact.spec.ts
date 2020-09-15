import { Matchers, Pact } from '@pact-foundation/pact';
import path from 'path';
import findProductById from '../services/product.service';

describe('Pact', () => {
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
            method: 'POST',
            path: '/grpc/sample.product.ProductEndPoint/findById',
            body: "\"1\""
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
      const response = await findProductById(1);
      
      expect(response).not.toBeNull();
    });

    afterEach(() => provider.verify());
  });

  afterAll(() => provider.finalize());
});
