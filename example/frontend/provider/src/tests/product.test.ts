import {
  Verifier,
  VerifierOptions,
} from '@pact-foundation/pact';
import { Request, Response } from 'express';
import { Product } from '../domain/product';
import ProductRepository from '../repository/product/product-repository';


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
        'product with ID 10 exists': async () => {
          ProductRepository.setProducts(new Map([
            [10, new Product(10, 'CREDIT_CARD', '28 Degrees')],
          ]))
        },
        'products exist': async () => {
          ProductRepository.setProducts(new Map([
            [9, new Product(9, 'CREDIT_CARD', 'Gem Visa')],
            [10, new Product(10, 'CREDIT_CARD', '28 Degrees')],
          ]))
        },
        'no products exist': async () => {
          ProductRepository.setProducts(new Map());
        },
        'product with ID 11 does not exist': async () => {
          ProductRepository.setProducts(new Map());
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
