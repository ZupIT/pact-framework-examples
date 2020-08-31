import ProductRepository from '../../repository/product-repository';

class ProductController {

  async getAll(req: any, res: any) {
    res.send(await ProductRepository.fetchAll());
  };

  async getById(req: any, res: any) {
    const product = await ProductRepository.getById(req.params.id);
    product ? res.send(product) : res.status(404).send({ message: 'Product not found' });
  };
}

export default new ProductController();