import axios, { AxiosResponse } from 'axios';
import { APP_URL, CLIENTS } from '../constants';

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
    const client = CLIENTS.find(clientID => clientID === id);

    if (!client) {
      throw new Error("Client doesn't exist");
    }

    const response: Response = await axios.get(`${APP_URL}/balance/${client}`);

    return response.data;
  }
}
