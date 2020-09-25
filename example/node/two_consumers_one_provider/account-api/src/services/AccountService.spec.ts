import { AccountService } from './AccountService';

jest.mock('axios');

describe('AccountService', () => {
  let accountService: AccountService;

  beforeAll(() => {
    accountService = new AccountService();
  });

  it('should get account when entering an existing ID', () => {
    const accountMock = {
      clientID: 1,
      balance: 100,
      name: 'Zézin',
      fantasyName: 'Empresa do Zézin',
    };

    const account = accountService.getAccountByClientID(1);

    expect(account).toEqual(accountMock);
  });

  it('should get an error when entering a non-existing ID', () => {
    expect(() => accountService.getAccountByClientID(9124214)).toThrowError(
      "Account doesn't exist",
    );
  });
});
