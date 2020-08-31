import productController from "./product-controller";
import { HttpRequest } from "../helpers/http";


describe('Product Controller', () => {
  describe('get product by id', () => {
    it('should return 400 if no id is provided', async () => {
      const httpRequest: HttpRequest = {
        body: 'any_body'
      }
      const httpResponse = await productController.getById(httpRequest)
      expect(httpResponse).toEqual({ statusCode: 400, body: 'Missing param error: id' })
    });
  });
  
});