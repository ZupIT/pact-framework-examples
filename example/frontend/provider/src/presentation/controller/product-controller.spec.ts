import productController from "./product-controller";
import { HttpRequest } from "../helpers/http";
import { badRequest } from "../helpers/http-errors";
import { MissingParamError } from "../errors/missing-param";


describe('Product Controller', () => {
  describe('get product by id', () => {
    it('should return 400 if no id is provided', async () => {
      const httpRequest: HttpRequest = {
        body: 'any_body'
      }
      const httpResponse = await productController.getById(httpRequest)
      expect(httpResponse).toEqual(badRequest(new MissingParamError('id')))
    });
  });
  
});