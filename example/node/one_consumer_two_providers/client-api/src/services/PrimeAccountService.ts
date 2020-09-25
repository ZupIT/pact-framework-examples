import axios, { AxiosResponse } from 'axios';
import { APP_URL, CLIENTS } from '../constants';

interface Response extends AxiosResponse {
  data: Prime;
}

interface Prime {
  isPrime: boolean;
  discountPercentageFee: number;
}

export class PrimeAccountService {
  public async getPrimeAccountDetailsByClientID(id: number): Promise<Prime> {
    const client = CLIENTS.find(clientID => clientID === id);

    if (!client) {
      throw new Error("Client doesn't exist");
    }

    const response: Response = await axios.get(`${APP_URL}/prime/${id}`);

    return response.data;
  }
}
