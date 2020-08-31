import ProductRepository from '../../repository/product-repository';
import { Request, Response } from 'express';

class ProductController {

  async getAll(req: Request, res: Response) {
    res.send(await ProductRepository.getAll());
  };

  async getById(req: Request, res: Response) {
    const product = await ProductRepository.getById(parseInt(req.params.id));
    product ? res.send(product) : res.status(404).send({ message: 'Produto não encontrado' });
  };

  async store(req: Request, res: Response) {
    const { id, type, name } = req.body;
    const product = await ProductRepository.store(id, type, name);
    product ? res.send(product.get(id)) : res.status(404).send({ message: 'Erro ao adicionar produto' })
  }

  async update(req: Request, res: Response) {
    const { id, type, name } = req.body;
    const product = await ProductRepository.update(id, type, name);
    product ? res.send(product.get(id)) : res.status(404).send({ message: 'Erro ao atualizar produto' })
  }

  async delete(req: Request, res: Response) {
    const id = parseInt(req.params.id);
    return ProductRepository.delete(id) 
    ? res.status(200).send({ message: 'Produto excluído' })
    : res.status(404).send({ message: 'Erro ao excluir produto' })
  }
}

export default new ProductController();