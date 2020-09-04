import {
  Verifier,
  VerifierOptions,
} from '@pact-foundation/pact';
import express, { Request, Response, json } from 'express';
import ProductRepository from '../repository/product/product-repository';
import { makeProductsMock } from './products-mock';
import routes from '../routes/products'


const param = process.argv.filter( it => it.includes("pact-broker-url"))
            .map( it => it.split("=")[1])[0];

const pactBrokerUrl = param || "http://localhost:9292";

describe('Pact Verification', () => {
  let app:any;

  beforeAll(async () => {
    app = express()
    .use(json())
    .use(routes)
    .listen(3333, () => console.log(`Provider listening on port ${3333}`))
  })

  it('validates the expectations of ProductService', async () => {
    const opts: VerifierOptions = {
      logLevel: 'info',
      providerBaseUrl: 'http://localhost:3333',
      provider: 'NodeProductApi',
      providerVersion: '1.0.0',
      pactBrokerUrl,
      publishVerificationResult: true,
      stateHandlers: {
        'list products': async () => {
          ProductRepository.setProducts(makeProductsMock())
        },
        'list one product': async () => {
          ProductRepository.setProducts(makeProductsMock())
        },
        'create a product': async () => {
          ProductRepository.setProducts(makeProductsMock());
        },
        'update a product': async () => {
          ProductRepository.setProducts(makeProductsMock());
        },
        'delete a product': async () => {
          ProductRepository.setProducts(makeProductsMock());
        },
      },

      requestFilter: (req: Request, res: Response, next: any) => {
        if (!req.headers.authorization) {
          next();
          return;
        }
        req.headers.authorization = `Bearer ${new Date().toISOString()}`;
        next();
      },
    };

    return new Verifier(opts).verifyProvider().then((output) => {
      console.log(output);
    })
    .finally(() => app.close());
  });
});
