import productRepository from './product-repository'

describe('Product Repository', () => {
  it('should return a list of products', async () => {
    const products = await productRepository.getAll()
    expect(products).toBeTruthy()
  });
});