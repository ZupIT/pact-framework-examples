import { ProductController } from "./product-controller";
import { HttpRequest } from "../protocols/http";
import { badRequest, success } from "../helpers/http-helpers";
import { MissingParamError } from "../errors/missing-param";
import { Product } from "../../domain/product";
import { Repository } from "../../repository/product-repository";

export const makeProductsMap = (): any => {
  return new Map([
    [9, new Product(9, "CREDIT_CARD", "Gem Visa")],
    [10, new Product(10, "CREDIT_CARD", "28 Degrees")],
    [11, new Product(11, "PERSONAL_LOAN", "MyFlexiPay")],
  ]);
}

const makeFakeProduct = (): Product => {
  return new Product(1, 'any_type', 'any_name')
}

const makeProductRepository = (): any => {
  class ProductRepositoryStub implements Repository {
    async getById(id: number): Promise<Product> {
      return new Promise(resolve => resolve(makeFakeProduct()))
    }

    async getAll(): Promise<Product[]> {
      return makeProductsMap()
    }
  }
  return new ProductRepositoryStub()
}

const makeSut = (): any => {
  const sut = new ProductController(makeProductRepository())
  return { sut }
}

describe('Product Controller', () => {
  describe('get product by id', () => {
    it('should return 400 if no id is provided', async () => {
      const { sut } = makeSut()
      const httpRequest: HttpRequest = {
        body: 'any_body'
      }
      const httpResponse = await sut.getById(httpRequest)
      expect(httpResponse).toEqual(badRequest(new MissingParamError('id')))
    });

    it('should return 200 if product exists', async () => {
      const { sut } = makeSut()
      const httpRequest: HttpRequest = {
        params: 1,
        body: 'any_body'
      }
      const httpResponse = await sut.getById(httpRequest)
      expect(httpResponse).toEqual(success(makeFakeProduct()))
    });

  });

  describe('get all products', () => {
    it('should return all products', async () => {
      const { sut } = makeSut()
      const httpResponse = await sut.getAll()
      expect(httpResponse).toEqual(success(makeProductsMap()));
    });
  });
});