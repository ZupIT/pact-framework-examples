import axios from 'axios';
import GetBalanceByClientID from '../services/GetBalanceByClientID';

jest.mock('axios');

describe('GetBalanceByClient', () => {
  it('balance', async () => {
    const mockedAxios = axios as jest.Mocked<typeof axios>;
    const mockValue = { data: { accountID: 1, clientID: 1, balance: 100 } };
    mockedAxios.get.mockImplementationOnce(() => Promise.resolve(mockValue));

    const response = await GetBalanceByClientID({ clientID: 1 });

    expect(response).toEqual(mockValue.data);
  });
});
