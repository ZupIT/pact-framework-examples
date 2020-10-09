import { AccountService } from '../services/account.service';
import { ClientOptions } from '@grpc/grpc-js';
import { Request, Response } from 'express';
import ClientRepository from '../repositories/client/client.repository';
import { Client } from '../repositories/client/client.interface';

export default class ClientController {

  accountService: AccountService;

  constructor(options?: ClientOptions) {
    this.accountService = new AccountService(options);
  }

  getBalance = async (req: Request, res: Response) => {
    const clientId = req.params.id;

    const client: Client | undefined = await ClientRepository.getById(clientId);

    if (!client) {
      return res.status(403);
    }
  
    try {
      const response = await this.accountService.getById(client.accountId);
      return res.status(200).json(response);
    } catch (error) {
      console.log(error);
      return res.status(500).json({ error: error.details });
    }
  }

}