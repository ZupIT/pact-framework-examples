import ProductRepository, { Repository } from '../../repository/product-repository';
import { Request, Response } from 'express';
import { HttpRequest, HttpResponse } from '../protocols/http';
import { badRequest, success } from '../helpers/http-helpers';
import { MissingParamError } from '../errors/missing-param';

interface Controller {
  getAll(req: Request, res: Response): Promise<any>
  getById(httpRequest: HttpRequest): Promise<HttpResponse>
}

export class ProductController implements Controller {
  
  constructor(private readonly repository: Repository) {}
  
  async getAll(req: Request, res: Response) {
    res.send(await this.repository.getAll());
  };

  async getById(httpRequest: HttpRequest): Promise<HttpResponse> {
    if (!httpRequest.params) {
      return badRequest(new MissingParamError('id'))
    }
    const { id } = httpRequest.params;
    const product = await this.repository.getById(parseInt(id));
    return success(product)
    // product ? res.send(product) : res.status(404).send({ message: 'Produto não encontrado' });
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
