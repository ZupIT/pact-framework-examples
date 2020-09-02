import {
  Verifier,
  VerifierOptions,
} from '@pact-foundation/pact';
import { Request, Response } from 'express';
import ProductRepository from '../repository/product/product-repository';
import { makeFakeProductsTests } from './product-factory-tests';


const param = process.argv.filter( it => it.includes("pact-broker-url"))
            .map( it => it.split("=")[1])[0];

const pactBrokerUrl = param || "http://localhost:9292";

describe('Pact Verification', () => {
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
          ProductRepository.setProducts(makeFakeProductsTests())
        },
        'list one product': async () => {
          ProductRepository.setProducts(makeFakeProductsTests())
        },
        'create a product': async () => {
          ProductRepository.setProducts(makeFakeProductsTests());
        },
        'update a product': async () => {
          ProductRepository.setProducts(makeFakeProductsTests());
        },
        'delete a product': async () => {
          ProductRepository.setProducts(makeFakeProductsTests());
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
    });
  });
});
