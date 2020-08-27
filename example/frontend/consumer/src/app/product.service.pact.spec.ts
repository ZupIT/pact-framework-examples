import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { Matchers, PactWeb } from '@pact-foundation/pact-web';
import { ProductService } from './product.service';
import { map } from 'rxjs/operators';


describe('ProductServicePact', () => {

  let provider;

  // Setup Pact mock server for this service
  beforeAll(async () => {

    provider = await new PactWeb();

    // required for slower CI environments
    await new Promise(resolve => setTimeout(resolve, 1000));

    // Required if run with `singleRun: false`
    await provider.removeInteractions();
  });

  // Configure Angular Testbed for this service
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientModule ],
      providers: [ ProductService ]
    });
  });

  // Verify mock service
  afterEach(async () => {
    await provider.verify();
  });

  // Create contract
  afterAll(async () => {
    await provider.finalize();
  });

  describe('getAll', () => {

    let productService: ProductService;

    beforeAll(async () => {
      await provider.addInteraction({
        state: `list products`,
        uponReceiving: 'a request to GET a list of products',
        withRequest: {
          method: 'GET',
          path: `/api/products`
        },
        willRespondWith: {
          status: 200,
          body: Matchers.eachLike({
            id: Matchers.integer(),
            type: Matchers.string(),
            name: Matchers.string(),
          })
        }
      });

    });
    
    beforeEach( async () => {
        productService = TestBed.inject(ProductService);
    })

    it('should get a list of products', async () => {
      await productService.getAll().pipe(
          map( it => it.status)
      ).toPromise().then( status => {
        expect(status).toBe(200);
      });
    });
  });

});
