import productRepository from './product-repository'

describe('Product Repository', () => {
  it('should return a list of products', async () => {
    const products = await productRepository.getAll()
    expect(products).toBeTruthy()
  });

  it('should get a product of list products', async () => {
    const product = await productRepository.getById(1)
    expect(product).toEqual({ id: 1, type: 'CREDIT_CARD', name: 'Gem Visa' })
  });
});