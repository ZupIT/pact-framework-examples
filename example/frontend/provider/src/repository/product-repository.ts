import { makeProducts } from '../util/factory/product-factory';

class ProductRepository {

  private products: any = makeProducts();

  setProducts(products: any) {
    this.products = products;
  }

  async fetchAll() {
    return [...this.products.values()];
  }

  async getById(id: number) {
    return this.products.get(id);
  }
}
export default new ProductRepository();
