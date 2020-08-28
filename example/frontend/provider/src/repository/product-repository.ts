import { Product } from '../domain/models/product';

export class ProductRepository {
  public products: any = new Map([
    ["09", new Product(9, "CREDIT_CARD", "Gem Visa")],
    ["10", new Product(10, "CREDIT_CARD", "28 Degrees")],
    ["11", new Product(11, "PERSONAL_LOAN", "MyFlexiPay")],
  ])

  constructor() {}

  async fetchAll() {
    return [...this.products.values()]
  }

  async getById(id: number) {
    return this.products.get(id);
  }
}
