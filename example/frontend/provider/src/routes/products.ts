import { Router } from 'express';
import ProductController from '../presentation/controller/product-controller';

const routes = Router();

routes.get("/api/products", ProductController.getAll);
routes.get("/api/products/:id", ProductController.getById);
routes.post("/api/products", ProductController.save);
routes.delete("/api/products/:id", ProductController.deleteById);
routes.put("/api/products/:id", ProductController.update);

export default routes;