import { ProductController } from "./product-controller";
import { HttpRequest } from "../protocols/http";
import { badRequest, success } from "../helpers/http-helpers";
import { MissingParamError } from "../errors/missing-param";
import { Product } from "../../domain/product";
import { Repository } from "../../repository/usecase/repository";


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

    async store(id: number, type: string, name: string): Promise<Product> {
      return makeFakeProduct()
    }

    async update(id: number, type: string, name: string): Promise<Product> {
      return new Product(1, 'updated_type', 'updated_name')
    }
  }
  return new ProductRepositoryStub()
}

interface SutTypes {
  sut: ProductController
}

const makeSut = (): SutTypes => {
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

  describe('save product', () => {
    it('should return 400 if no id is provided', async () => {
      const { sut } = makeSut()
      const httpRequest: HttpRequest = {
        body: {
          type: 'any_type',
          name: 'any_name'
        }
      }
      const httpResponse = await sut.save(httpRequest)
      expect(httpResponse).toEqual(badRequest(new MissingParamError('id')))
    });

    it('should return 400 if no type is provided', async () => {
      const { sut } = makeSut()
      const httpRequest: HttpRequest = {
        body: {
          id: 1,
          name: 'any_name'
        }
      }
      const httpResponse = await sut.save(httpRequest)
      expect(httpResponse).toEqual(badRequest(new MissingParamError('type')))
    });

    it('should return 400 if no name is provided', async () => {
      const { sut } = makeSut()
      const httpRequest: HttpRequest = {
        body: {
          id: 1,
          type: 'any_type',
        }
      }
      const httpResponse = await sut.save(httpRequest)
      expect(httpResponse).toEqual(badRequest(new MissingParamError('name')))
    });

    it('should return 200 if save product success', async () => {
      const { sut } = makeSut()
      const httpRequest: HttpRequest = {
        body: {
          id: 1,
          type: 'any_type',
          name: 'any_name'
        }
      }
      const httpResponse = await sut.save(httpRequest)
      expect(httpResponse).toEqual(success(makeFakeProduct()))
    });
  });

  describe('update product', () => {
    it('should return 200 if product is updated', async () => {
      const { sut } = makeSut()
      const httpRequest = {
        body: {
          id: 1,
          type: 'updated_type',
          name: 'updated_name'
        }
      }
      const httpResponse = await sut.update(httpRequest)
      expect(httpResponse).toEqual(success({ id: 1, type: 'updated_type', name: 'updated_name' }));
    });
  });
});