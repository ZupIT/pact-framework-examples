import { ProductRepository } from '../repository/product-repository';

export const repository = new ProductRepository();

export const getAll = async (req: any, res: any) => {
  res.send(await repository.fetchAll());
};

export const getById = async (req: any, res: any) => {
  const product = await repository.getById(req.params.id);
  product ? res.send(product) : res.status(404).send({ message: 'Product not found' });
};
