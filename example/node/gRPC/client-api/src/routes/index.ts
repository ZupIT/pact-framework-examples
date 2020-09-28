import { Router } from 'express';
import ClientController from '../controllers/client.controller';

const routes = Router();
const clientController = new ClientController();

routes.get('/client/:id/balance', clientController.getBalance );

export default routes;
