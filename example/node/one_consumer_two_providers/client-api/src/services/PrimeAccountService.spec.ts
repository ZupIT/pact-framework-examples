import axios from 'axios';
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
      1,
    );

    expect(response).toEqual(mockValue.data);
  });

  it('should get an error when entering a non-existing ID', async () => {
    await expect(
      primeAccountService.getPrimeAccountDetailsByClientID(919119),
    ).rejects.toThrowError("Client doesn't exist");
  });
});
