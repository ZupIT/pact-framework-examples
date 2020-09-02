import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { Matchers, PactWeb } from '@pact-foundation/pact-web';
import { Product, ProductService } from './product.service';

describe('ProductServicePact', () => {
  let provider: PactWeb;
  let productService: ProductService;

  // Setup Pact mock server for this service
  beforeAll(async () => {
    provider = new PactWeb();

    // required for slower CI environments
    await new Promise((resolve) => setTimeout(resolve, 1000));

    // Required if run with `singleRun: false`
    await provider.removeInteractions();
  });

  // Configure Angular Testbed for this service
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [ProductService],
    });

    productService = TestBed.inject(ProductService);
  });

  // Verify mock service
  afterEach(async () => provider.verify());

  // Create contract
  afterAll(async () => provider.finalize());

  describe('getAll', () => {
    beforeAll(async () => {
      await provider.addInteraction({
        state: `list products`,
        uponReceiving: 'a request to GET a list of products',
        withRequest: {
          method: 'GET',
          path: `/api/products`,
        },
        willRespondWith: {
          status: 200,
          body: Matchers.eachLike({
            id: Matchers.integer(),
            type: Matchers.string(),
            name: Matchers.string(),
          }),
        },
      });
    });

    it('should get a list of products', async () => {
      await productService
        .getAll()
        .toPromise()
        .then((res) => expect(res.status).toBe(200));
    });
  });

  describe('getOne', () => {
    beforeAll(async () => {
      await provider.addInteraction({
        state: `list one product`,
        uponReceiving: 'a request to GET a one product',
        withRequest: {
          method: 'GET',
          path: `/api/products/1`,
        },
        willRespondWith: {
          status: 200,
          body: {
            id: Matchers.integer(),
            type: Matchers.string(),
            name: Matchers.string(),
          },
        },
      });
    });

    it('should get a product', async () => {
      await productService
        .getOne(1)
        .toPromise()
        .then((res) => expect(res.status).toBe(200));
    });
  });

  describe('create', () => {
    beforeAll(async () => {
      await provider.addInteraction({
        state: `create a product`,
        uponReceiving: 'a request to POST a product',
        withRequest: {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          path: `/api/products`,
          body: {
            id: Matchers.integer(),
            type: Matchers.string(),
            name: Matchers.string(),
          },
        },
        willRespondWith: {
          status: 200,
          body: {
            id: Matchers.integer(),
            type: Matchers.string(),
            name: Matchers.string(),
          },
        },
      });
    });

    it('should create a product', async () => {
      const product: Product = {
        id: Matchers.integer().getValue(),
        type: Matchers.string().getValue(),
        name: Matchers.string().getValue(),
      };

      await productService
        .create(product)
        .toPromise()
        .then((res) => expect(res.status).toBe(200));
    });
  });

  describe('update', () => {
    beforeAll(async () => {
      await provider.addInteraction({
        state: `update a product`,
        uponReceiving: 'a request to PUT a product',
        withRequest: {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          path: `/api/products/1`,
          body: {
            type: Matchers.string(),
            name: Matchers.string(),
          },
        },
        willRespondWith: {
          status: 200,
          body: {
            id: Matchers.integer(),
            type: Matchers.string(),
            name: Matchers.string(),
          },
        },
      });
    });

    it('should update a product', async () => {
      const product: Omit<Product, 'id'> = {
        type: Matchers.string().getValue(),
        name: Matchers.string().getValue(),
      };

      await productService
        .update(1, product)
        .toPromise()
        .then((res) => expect(res.status).toBe(200));
    });
  });

  describe('delete', () => {
    beforeAll(async () => {
      await provider.addInteraction({
        state: `delete a product`,
        uponReceiving: 'a request to DELETE a product',
        withRequest: {
          method: 'DELETE',
          path: `/api/products/1`,
        },
        willRespondWith: {
          status: 200,
        },
      });
    });

    it('should delete a product', async () => {
      await productService
        .delete(1)
        .toPromise()
        .then((res) => expect(res.status).toBe(200));
    });
  });
});
