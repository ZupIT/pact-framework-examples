import axios from 'axios';
import { APP_URL, EXISTENT_CLIENT_ID } from '../constants';
import { AccountService } from './AccountService';

jest.mock('axios');

describe('AccountService', () => {
  let accountService: AccountService;

  beforeAll(() => {
    accountService = new AccountService();
  });

  it('should get account when entering an existing ID', async () => {
    const mockedAxios = axios as jest.Mocked<typeof axios>;
    const mockValue = { data: { accountID: 1, clientID: 1, balance: 100 } };
    mockedAxios.get.mockImplementationOnce(() => Promise.resolve(mockValue));

    const response = await accountService.getBalanceByClientID(
      EXISTENT_CLIENT_ID
    ).then();

    expect(axios.get).toHaveBeenCalledWith(
      `${APP_URL}/account/${EXISTENT_CLIENT_ID}`,
    );
    expect(response).toEqual(mockValue);
  });
});
