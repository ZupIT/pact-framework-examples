import { Router } from 'express';
import { ClientController } from '../controllers/ClientController';

const routes = Router();
const clientController = new ClientController();

routes.use((req, _res, next) => {
  const { method, url } = req;

  console.log(`[${method.toUpperCase()}] ${url}`);

  return next();
});

routes.get('/client/:id', clientController.getClient);

export default routes;
