import { makeProducts } from '../util/factory/product-factory';
import { Product } from '../domain/product';

export interface Repository {
  getAll(): Promise<Product[]>
  getById(id: number): Promise<Product>
}

class ProductRepository implements Repository {

  private products: Map<number, Product> = makeProducts();

  setProducts(products: any) {
    this.products = products;
  }

  async getAll() {
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

  async delete(id: number): Promise<boolean> {
    return this.products.delete(id)
  }
}
export default new ProductRepository();
