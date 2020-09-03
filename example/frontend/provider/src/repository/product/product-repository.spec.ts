import productRepository from './product-repository'
import { Product } from '../../domain/product';

describe('Product Repository', () => {

  beforeEach(() => {
    productRepository.setProducts(new Map([
      [1, new Product(1, "CREDIT_CARD", "Gem Visa")],
      [2, new Product(2, "CREDIT_CARD", "28 Degrees")],
      [3, new Product(3, "PERSONAL_LOAN", "MyFlexiPay")],
      [4, new Product(4, "CREDIT_CARD", "MyFlexiPayCredit")],
      [5, new Product(5, "PERSONAL_LOAN", "MyFlexiPay")],
    ]))
  })

  it('should return a list of products', async () => {
    const products = await productRepository.getAll()
    expect(products).toBeTruthy()
  });

  it('should get a product of list products', async () => {
    const product = await productRepository.getById(1)
    expect(product).toEqual({ id: 1, type: 'CREDIT_CARD', name: 'Gem Visa' })
  });

  it('should save new product in list products', async () => {
    await productRepository.store(10, 'Any_Type', 'Any_Name');
    const product = await productRepository.getById(10)
    expect(product).toEqual({ id: 10, type: 'Any_Type', name: 'Any_Name' })
  });

  it('should update a product', async () => {
    await productRepository.update(2, 'Any_Updated', 'Any_Updated');
    const product = await productRepository.getById(2)
    expect(product).toEqual({ id: 2, type: 'Any_Updated', name: 'Any_Updated' })
  });

  it('should delete a product', async () => {
    const product = await productRepository.delete(5);
    expect(product).toBeTruthy()
  });
});