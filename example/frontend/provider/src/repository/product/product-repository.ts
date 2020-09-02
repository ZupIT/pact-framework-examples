import { makeProducts } from '../../util/factory/product-factory';
import { Product } from '../../domain/product';
import { Repository } from '../usecase/repository';

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

  async store(id: number, type: string, name: string): Promise<Product> {
    const product = this.products.set(id, new Product(id, type, name))
    return product.get(id);
  }

  async update(id: number, type: string, name: string): Promise<Product> {
    if (!this.products.get(id)) {
      return null
    }
    const product = this.products.set(id, new Product(id, type, name))
    return product.get(id)
  }

  async delete(id: number): Promise<boolean> {
    if (!this.products.get(id)) {
      return null
    }
    return this.products.delete(id)
  }
}
export default new ProductRepository();
