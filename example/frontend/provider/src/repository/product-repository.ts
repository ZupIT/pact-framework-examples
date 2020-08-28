import { makeProducts } from '../factory/product-factory';

export class ProductRepository {
  
  constructor(public products: any = makeProducts()) {}

  async fetchAll() {
    return [...this.products.values()]
  }

  async getById(id: number) {
    return this.products.get(id);
  }
}
