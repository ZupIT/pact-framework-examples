import { Account } from './account.interface';
import AccountRepository from './account.repository';

describe('AccountRepository', () => {
  let ACCOUNT_MOCK: Map<string, Account>;
  let EXISTING_ACCOUNT_ID: string;

  beforeEach(() => {
    EXISTING_ACCOUNT_ID = '15';
    ACCOUNT_MOCK = new Map([
      ['15', { id: 15, balance: 200.0, accountType: 'checking' }],
    ]);
  });

  it('should get all clients', async () => {
    const mockValues = Array.from(ACCOUNT_MOCK.values());

    const clients = await AccountRepository.getAll();

    expect(clients).toEqual(mockValues);
  });

  it('should get specific client entering existing id', async () => {
    const mockValue = ACCOUNT_MOCK.get(EXISTING_ACCOUNT_ID);

    const client = await AccountRepository.getById(EXISTING_ACCOUNT_ID);

    expect(client).toEqual(mockValue);
  });

  it('should store new client', async () => {
    const newClient = await AccountRepository.store({
      id: 123,
      accountType: 'checking',
      balance: 123.11,
    });

    const allClients = await AccountRepository.getAll();

    expect(allClients).toContain(newClient);
  });

  it('should update an existing client', async () => {
    const newAccountType = 'new type';
    const existingAccount = ACCOUNT_MOCK.get(EXISTING_ACCOUNT_ID);

    const updatedClient = await AccountRepository.update({
      id: Number(existingAccount?.id),
      balance: Number(existingAccount?.balance),
      accountType: newAccountType,
    });

    const client = await AccountRepository.getById(EXISTING_ACCOUNT_ID);

    expect(updatedClient).toEqual(client);
  });

  it('should delete existing client', async () => {
    const deletedAccount = await AccountRepository.delete(EXISTING_ACCOUNT_ID);

    expect(deletedAccount).toEqual(true);
  });
});
