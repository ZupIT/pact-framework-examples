import { Router } from 'express';
import findProductById from '../services/product.service';

const routes = Router();

routes.use((req, _res, next) => {
  const { method, url } = req;

  console.log(`[${method.toUpperCase()}] ${url}`);

  return next();
});

routes.get('/product/:id', async (req, res) => {
  
  const productId = req.params.id;

  try {
    const response = await findProductById(Number(productId));
    return res.status(200).json(response);
  } catch (error) {
    console.log(error);
    return res.status(500);
  }

});

export default routes;
