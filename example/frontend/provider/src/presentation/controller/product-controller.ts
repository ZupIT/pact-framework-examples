import ProductRepository from '../../repository/product/product-repository';
import { Request, Response } from 'express';
import { HttpRequest, HttpResponse } from '../protocols/http';
import { badRequest, success, notFound } from '../helpers/http-helpers';
import { MissingParamError } from '../errors/missing-param';
import { Controller } from '../protocols/controller';
import { Repository } from '../../repository/usecase/repository';

export class ProductController implements Controller {
  
  constructor(private readonly repository: Repository) {}
  
  async getAll(): Promise<HttpResponse> {
    const products = await this.repository.getAll()
    return success(products)
  };

  async getById(httpRequest: HttpRequest): Promise<HttpResponse> {
    if (!httpRequest.params) {
      return badRequest(new MissingParamError('id'))
    }
    const { id } = httpRequest.params;
    const product = await this.repository.getById(id);
    if (!product) {
      return notFound('Nenhum produto encontrado')
    }
    return success(product)
  };

  async save(httpRequest: HttpRequest): Promise<HttpResponse> {
    const requiredFields = ['id', 'type', 'name'];
    for(const field of requiredFields) {
      if(!httpRequest.body[field]) {
        return badRequest(new MissingParamError(field))
      }
    }
    const { id, type, name } = httpRequest.body;
    const product = await ProductRepository.store(id, type, name);
    return null
    // product ? res.send(product.get(id)) : res.status(404).send({ message: 'Erro ao adicionar produto' })
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
