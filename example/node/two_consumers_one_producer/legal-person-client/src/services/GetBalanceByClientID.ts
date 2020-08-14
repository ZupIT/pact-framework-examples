import axios, { AxiosResponse } from 'axios';
import { APP_URL } from '../constants';

interface Response extends AxiosResponse {
  data: Account;
}

interface Account {
  clientID: number;
  accountID: number;
  balance: number;
}

export default async function GetBalanceByClientID({
  clientID,
}: {
  clientID: number;
}): Promise<Account> {
  const response: Response = await axios.get(`${APP_URL}/balance/${clientID}`);

  return response.data;
}
