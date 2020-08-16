import { Router } from 'express';
import GetBalanceByClientID from '../services/GetBalanceByClientID';

const routes = Router();

routes.use((req, _res, next) => {
  const { method, url } = req;

  console.log(`[${method.toUpperCase()}] ${url}`);

  return next();
});

routes.get('/client/:id', async (req, res) => {
  const { id } = req.params;

  if (Number(id) !== 1) {
    return res.status(404).json({ message: "Client doesn't exist" });
  }

  const { accountID, balance, clientID } = await GetBalanceByClientID({
    clientID: Number(id),
  });

  return res.status(200).json({ accountID, balance, clientID });
});

export default routes;
