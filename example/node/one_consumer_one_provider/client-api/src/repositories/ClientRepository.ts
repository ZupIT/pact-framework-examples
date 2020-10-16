import { CLIENTS } from '../constants';

export class ClientRepository {
  findClientByID(id: number): number {
    const client = CLIENTS.find(clientId => clientId === id);

    if (!client) {
      throw new Error("Client doesn't exist");
    }

    return client;
  }
}
