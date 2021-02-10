import { EXISTENT_CLIENT_ID, NON_EXISTENT_CLIENT_ID } from '../constants';
import { AccountRepository } from './AccountRepository';

jest.mock('axios');

describe('AccountRepository', () => {
  let accountRepository: AccountRepository;

  beforeAll(() => {
    accountRepository = new AccountRepository();
  });

  it('should get account when entering an existing ID', () => {
    const accountMock = {
      clientID: 1,
      accountID: 1,
      balance: 100,
    };

    const account = accountRepository.getAccountByClientID(EXISTENT_CLIENT_ID);

    expect(account).toEqual(accountMock);
  });

  it('should get an error when entering a non-existing ID', () => {
    expect(() =>
      accountRepository.getAccountByClientID(NON_EXISTENT_CLIENT_ID),
    ).toThrowError("Account doesn't exist");
  });
});
