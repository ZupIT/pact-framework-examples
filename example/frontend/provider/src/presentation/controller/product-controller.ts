import ProductRepository from '../../repository/product-repository';
import { Request, Response } from 'express';

class ProductController {

  async getAll(req: any, res: any) {
    res.send(await ProductRepository.fetchAll());
  };

  async getById(req: Request, res: Response) {
    const product = await ProductRepository.getById(parseInt(req.params.id));
    product ? res.send(product) : res.status(404).send({ message: 'Product not found' });
  };

  async store(req: Request, res: Response) {
    const { id, type, name } = req.body;
    const product = await ProductRepository.store(id, type, name);
    product ? res.send(product.get(id)) : res.status(404).send({ message: 'Erro ao adicionar produto' })
  }
}

export default new ProductController();