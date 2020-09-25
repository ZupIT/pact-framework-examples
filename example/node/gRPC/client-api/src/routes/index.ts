import { Router } from 'express';
import ProductController from '../controlles/product.controller';

const routes = Router();
const productService = new ProductController();

routes.use((req, _res, next) => {
  const { method, url } = req;

  console.log(req.path);

  console.log(`[${method.toUpperCase()}] ${url}`);

  return next();
});

routes.get('/product/:id', productService.findById );

export default routes;
