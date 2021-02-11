import axios, { AxiosResponse } from 'axios';
import { APP_URL } from '../constants';
import { ClientRepository } from '../repositories/ClientRepository';

interface Response extends AxiosResponse {
  data: Account;
}

export interface Account {
  name: string;
  clientID: number;
  balance: number;
}

export class AccountService {
  async getBalanceByClientID(id: number): Promise<Account> {
    const clientRepository = new ClientRepository();
    const client = clientRepository.findClientByID(id);

    const response: Response = await axios.get(`${APP_URL}/balance/${client}`);

    return response.data;
  }
}
