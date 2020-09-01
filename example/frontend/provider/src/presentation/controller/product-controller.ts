import { HttpRequest, HttpResponse } from '../protocols/http';
import { badRequest, success, notFound, serverError } from '../helpers/http-helpers';
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
    const product = await this.repository.getById(parseInt(id));
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
    const product = await this.repository.store(id, type, name);
    return success(product)
  }

  async update(httpRequest: HttpRequest): Promise<HttpResponse> {
    const { id, type, name } = httpRequest.body;
    const product = await this.repository.update(id, type, name);
    return success(product);
  }

  async delete(httpRequest: HttpRequest): Promise<HttpResponse> {
    const { id } = httpRequest.params;
    const deleted = await this.repository.delete(parseInt(id));
    if (!deleted) {
      return serverError({ body: 'Erro inesperado' });
    }
    return success({ body: 'Produto excluído com sucesso' });
  }
}
