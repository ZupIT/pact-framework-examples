import { ProductService } from './../services/product.service';
import { ClientOptions } from '@grpc/grpc-js';
import { Request, Response } from 'express';
export default class ProductController {

  productService: ProductService;

  constructor(options?: ClientOptions) {
    this.productService = new ProductService(options);
  }

  findById = async (req: Request, res: Response) => {
    const productId = req.params.id;
  
    try {
      const response = await this.productService.findById(productId);
      return res.status(200).json(response);
    } catch (error) {
      console.log(error);
      return res.status(500);
    }
  }

}