import axios from 'axios';
import { APP_URL, EXISTENT_CLIENT_ID } from '../constants';
import { PrimeAccountService } from './PrimeAccountService';

jest.mock('axios');

describe('PrimeAccountService', () => {
  let primeAccountService: PrimeAccountService;

  beforeAll(() => {
    primeAccountService = new PrimeAccountService();
  });

  it('should get account when entering an existing ID', async () => {
    const mockedAxios = axios as jest.Mocked<typeof axios>;
    const mockValue = { data: { isPrime: true, discountPercentageFee: 0.5 } };
    mockedAxios.get.mockImplementationOnce(() => Promise.resolve(mockValue));

    const response = await primeAccountService.getPrimeAccountDetailsByClientID(
      EXISTENT_CLIENT_ID,
    );

    expect(axios.get).toHaveBeenCalledWith(
      `${APP_URL}/prime/${EXISTENT_CLIENT_ID}`,
    );
    expect(response).toEqual(mockValue.data);
  });
});
