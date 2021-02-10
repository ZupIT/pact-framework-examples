import { EXISTENT_CLIENT_ID, NON_EXISTENT_CLIENT_ID } from '../constants';
import { ClientRepository } from './ClientRepository';

describe('ClientRepository', () => {
  let clientRepository: ClientRepository;

  beforeAll(() => {
    clientRepository = new ClientRepository();
  });

  it('should get ID of existent client', () => {
    const client = clientRepository.findClientByID(EXISTENT_CLIENT_ID);

    expect(client).toEqual(EXISTENT_CLIENT_ID);
  });

  it('should get an error when entering a non-existing ID', () => {
    expect(() =>
      clientRepository.findClientByID(NON_EXISTENT_CLIENT_ID),
    ).toThrowError("Client doesn't exist");
  });
});
