import { AxiosResponse } from 'axios';
import { Request, Response } from 'express';
import { AccountService } from '../services/AccountService';

export class ClientController {
  public async getClient(req: Request, res: Response): Promise<Response> {
    const { id } = req.params;
    const accountService = new AccountService();

    try {
      const client: AxiosResponse = await accountService.getBalanceByClientID(Number(id));

      return res.status(200).json(client.data);
    } catch (err) {
      return res.status(404).json({ message: err.message });
    }
  }
}
