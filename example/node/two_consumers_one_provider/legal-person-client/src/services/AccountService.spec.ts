import axios from 'axios';
import { AccountService } from './AccountService';

jest.mock('axios');

describe('AccountService', () => {
  let accountService: AccountService;

  beforeAll(() => {
    accountService = new AccountService();
  });

  it('should get account when entering an existing ID', async () => {
    const mockedAxios = axios as jest.Mocked<typeof axios>;
    const mockValue = {
      data: { fantasyName: 'Empresa do Zézin', clientID: 2, balance: 1000 },
    };
    mockedAxios.get.mockImplementationOnce(() => Promise.resolve(mockValue));

    const response = await accountService.getBalanceByClientID(2);

    expect(response).toEqual(mockValue.data);
  });

  it('should get an error when entering a non-existing ID', async () => {
    await expect(
      accountService.getBalanceByClientID(919119),
    ).rejects.toThrowError("Client doesn't exist");
  });
});
