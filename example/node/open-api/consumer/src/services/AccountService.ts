import axios, { AxiosResponse } from 'axios';
import { APP_URL } from '../constants';
import { ClientRepository } from '../repositories/ClientRepository';

interface Response extends AxiosResponse {
  data: Account;
}

export interface Account {
  id: number;
  balance: number;
  accountType: string;
}

export class AccountService {
  getBalanceByClientID(id: number): Promise<Response> {
    const clientRepository = new ClientRepository();
    const accountId = clientRepository.findClientByID(id);

    return axios.get(`${APP_URL}/account/${accountId}`);
  }
}
