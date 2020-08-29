import { makeProducts } from '../util/factory/product-factory';

class ProductRepository {

  constructor(public products: any = makeProducts()) {}

  async fetchAll() {
    return [...this.products.values()];
  }

  async getById(id: number) {
    return this.products.get(id);
  }
}
export default new ProductRepository();
