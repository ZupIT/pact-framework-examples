import { makeProducts } from '../util/factory/product-factory';
import { Product } from '../domain/product';

interface ProductModel {
  type: string
  name: string
}

class ProductRepository {

  private products: Map<number, Product> = makeProducts();

  setProducts(products: any) {
    this.products = products;
  }

  async fetchAll() {
    return [...this.products.values()];
  }

  async getById(id: number) {
    return this.products.get(id);
  }

  async store(id: number, type: string, name: string): Promise<Map<number, Product>> {
    const product = this.products.set(id, new Product(id, type, name))
    return product;
  }

  async update(id: number, type: string, name: string): Promise<Map<number, Product>> {
    const product = this.products.set(id, new Product(id, type, name))
    return product
  }
}
export default new ProductRepository();
