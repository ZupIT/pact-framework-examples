import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { Matchers } from '@pact-foundation/pact';
import { PactWeb } from '@pact-foundation/pact-web';
import { resolve } from 'path';
import { ProductService } from './product.service';

describe('Api Pact test', () => {
  let provider;
  beforeAll(async () => {
    const provider = new PactWeb({
      host: 'http://localhost',
      port: 1234,
      // consumer: 'Consumer',
      // provider: 'Provider',
      log: resolve(process.cwd(), 'logs', 'pact.log'),
      dir: resolve(process.cwd(), 'pacts'),
      spec: 3,
    });

    // provider = new Pact({
    //   host: 'http://localhost',
    //   port: 1234,
    //   consumer: 'Consumer',
    //   provider: 'Provider',
    //   log: resolve(process.cwd(), 'logs', 'pact.log'),
    //   dir: resolve(process.cwd(), 'pacts'),
    //   spec: 3,
    // });

    // await provider.setup();
  });
  afterEach(() => provider.verify());
  afterAll(() => provider.finalize());
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [ProductService],
    });
  });

  describe('getting all products', () => {
    it('products exists', async () => {
      await provider.addInteraction({
        state: 'products exists',
        uponReceiving: 'get all products',
        withRequest: {
          method: 'GET',
          path: '/products',
        },
        willRespondWith: {
          status: 200,
          headers: {
            'Content-Type': 'application/json; charset=utf-8',
          },
          body: Matchers.eachLike({
            id: Matchers.integer(),
            type: Matchers.string(),
            name: Matchers.string(),
          }),
        },
      });
      const productService: ProductService = TestBed.get(ProductService);

      productService.getAllProducts().subscribe((res) => {
        console.log(res);
        expect(res).toBeGreaterThan(1);
      });
    });

    // it('no products exists', async () => {
    //   await provider.addInteraction({
    //     state: 'products exists',
    //     uponReceiving: 'get all products',
    //     withRequest: {
    //       method: 'GET',
    //       path: '/products',
    //     },
    //     willRespondWith: {
    //       status: 200,
    //       headers: {
    //         'Content-Type': 'application/json; charset=utf-8',
    //       },
    //       body: [],
    //     },
    //   });

    //   const productService: ProductService = TestBed.get(ProductService);

    //   // const products = await productService.getAllProducts();

    //   // const products = await axios.get(
    //   //   `${provider.mockService.baseUrl}/products`
    //   // );

    //   expect(products.data.length).toEqual([]);
    // });
  });
});
