import axios, { AxiosResponse } from 'axios';
import { APP_URL } from '../constants';

interface Response extends AxiosResponse {
  data: Prime;
}

interface Prime {
  isPrime: boolean;
  discountPercentageFee: number;
}

export default async function GetPrimeAccountDetails({
  clientID,
}: {
  clientID: number;
}): Promise<Prime> {
  const response: Response = await axios.get(`${APP_URL}/prime/${clientID}`);

  return response.data;
}
