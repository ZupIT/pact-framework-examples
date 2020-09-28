import axios, { AxiosResponse } from 'axios';
import { APP_URL } from '../constants';
import { ClientRepository } from '../repositories/ClientRepository';

interface Response extends AxiosResponse {
  data: Prime;
}

interface Prime {
  clientID: number;
  isPrime: boolean;
  discountPercentageFee: number;
}

export class PrimeAccountService {
  public async getPrimeAccountDetailsByClientID(id: number): Promise<Prime> {
    const clientRepository = new ClientRepository();
    const client = clientRepository.findClientByID(id);

    const response: Response = await axios.get(`${APP_URL}/prime/${client}`);

    return response.data;
  }
}
