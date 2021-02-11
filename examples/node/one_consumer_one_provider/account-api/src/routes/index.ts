import { Router } from 'express';
import { AccountController } from '../controllers/AccountController';

const routes = Router();
const accountController = new AccountController();

routes.use((req, _res, next) => {
  const { method, url } = req;

  console.log(`[${method.toUpperCase()}] ${url}`);

  return next();
});

routes.get('/balance/:clientID', accountController.getAccountWithBalance);

export default routes;
