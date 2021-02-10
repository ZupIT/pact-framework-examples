import { Router } from 'express';
import { PrimeAccountController } from '../controllers/PrimeAccountController';

const routes = Router();
const primeAccountController = new PrimeAccountController();

routes.use((req, _res, next) => {
  const { method, url } = req;

  console.log(`[${method.toUpperCase()}] ${url}`);

  return next();
});

routes.get('/prime/:clientID', primeAccountController.getPrimeAccount);

export default routes;
