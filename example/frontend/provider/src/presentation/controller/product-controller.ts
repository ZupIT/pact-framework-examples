import { Request, Response } from 'express';
import productRepository from '../../repository/product/product-repository';
import { Controller } from '../protocols/controller';

class ProductController implements Controller {
  
  async getAll(req: Request, res: Response) {
    res.send(await productRepository.getAll())
  }
  
  async getById(req: Request, res: Response) {
    const product = await productRepository.getById(parseInt(req.params.id))
    product ? res.send(product) : res.status(404).send({message: "Product not found"})
  }
  
  async save(req: Request, res: Response) {
    const { id, name, type } = req.body
    const product = await productRepository.store(parseInt(id), type, name)
    product ? res.status(200).send(product) : res.status(404).send({message: "Product not created"})
  }
  
  async deleteById(req: Request, res: Response) {
    const product = await productRepository.delete(parseInt(req.params.id))
    product ? res.status(200).send() : res.status(404).send({message: "Product not found"})
  }
  
  async update(req: Request, res: Response) {
    const { name, type } = req.body
    const product = await productRepository.update(parseInt(req.params.id), type, name)
    product ? res.status(200).send(product) : res.status(404).send({message: "Product not found"})
  }
}

export default new ProductController()
