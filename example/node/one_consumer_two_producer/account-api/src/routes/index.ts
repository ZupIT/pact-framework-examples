import { Router } from 'express';
import { accountMocked } from '../constants';

const routes = Router();

routes.use((req, _res, next) => {
  const { method, url } = req;

  console.log(`[${method.toUpperCase()}] ${url}`);

  return next();
});

routes.get('/balance/:clientID', async (req, res) => {
  const { clientID } = req.params;

  if (Number(clientID) !== 1) {
    return res.status(404).json({ message: "Client doesn't exist" });
  }

  return res.status(200).json(accountMocked);
});

export default routes;
