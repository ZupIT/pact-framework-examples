const Product = require("./product");

class ProductRepository {
  constructor() {
    this.products = new Map([
      ["09", new Product(9, "CREDIT_CARD", "Gem Visa")],
      ["10", new Product(10, "CREDIT_CARD", "28 Degrees")],
      ["11", new Product(11, "PERSONAL_LOAN", "MyFlexiPay")],
    ]);
  }

  async fetchAll() {
    return [...this.products.values()];
  }

  async getById(id) {
    return this.products.get(id);
  }
}

module.exports = ProductRepository;
