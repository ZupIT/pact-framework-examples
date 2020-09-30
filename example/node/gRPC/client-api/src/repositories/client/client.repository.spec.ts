import { Client } from './client.interface';
import ClientRepository, { clientsMock } from './client.repository';

describe('ClientRepository', () => {
  let CLIENTS_MOCK: Map<string, Client>;
  let EXISTING_CLIENT_ID: string;

  beforeEach(() => {
    EXISTING_CLIENT_ID = '1';
    CLIENTS_MOCK = clientsMock();
  });

  it('should get all clients', async () => {
    const mockValues = Array.from(CLIENTS_MOCK.values());

    const clients = await ClientRepository.getAll();

    expect(clients).toEqual(mockValues);
  });

  it('should get specific client entering existing id', async () => {
    const mockValue = CLIENTS_MOCK.get(EXISTING_CLIENT_ID);

    const client = await ClientRepository.getById(EXISTING_CLIENT_ID);

    expect(client).toEqual(mockValue);
  });

  it('should store new client', async () => {
    const newClient = await ClientRepository.store({
      id: 123,
      accountId: 123,
      clientName: 'The Boss',
    });
    const allClients = await ClientRepository.getAll();

    expect(allClients).toContain(newClient);
  });

  it('should update an existing client', async () => {
    const newName = 'John Due Client';

    const updatedClient = await ClientRepository.update({
      id: Number(EXISTING_CLIENT_ID),
      accountId: 15,
      clientName: newName,
    });
    const client = await ClientRepository.getById(EXISTING_CLIENT_ID);

    expect(updatedClient).toEqual(client);
  });

  it('should delete existing client', async () => {
    await ClientRepository.delete(EXISTING_CLIENT_ID);
    const client = await ClientRepository.getById(EXISTING_CLIENT_ID);

    expect(client).toBeUndefined();
  });
});
