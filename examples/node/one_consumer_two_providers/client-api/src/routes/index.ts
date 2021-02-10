import { Router } from 'express';
import { ClientController } from '../controllers/ClientController';
import { PrimeAccountController } from '../controllers/PrimeAccountController';

const routes = Router();
const clientController = new ClientController();
const primeAccountController = new PrimeAccountController();

routes.use((req, _res, next) => {
  const { method, url } = req;

  console.log(`[${method.toUpperCase()}] ${url}`);

  return next();
});

routes.get('/client/:id', clientController.getClient);
routes.get('/prime/:id', primeAccountController.getPrimeAccount);

export default routes;
