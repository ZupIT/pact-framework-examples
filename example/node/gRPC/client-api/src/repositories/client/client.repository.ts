import { Repository } from "../repository.interface";
import { Client } from "./client.interface";

export function clientsMock() {
    return new Map([
        ['1', { id: 1 , accountId: 15, clientName: 'John Client' }]
    ]);
}

export class ClientRepository implements Repository<Client> {

    private clients: Map<string, Client> = clientsMock();
    
    getAll(): Promise<Client[]> {
        return Promise.resolve(Array.from(this.clients.values()));
    }

    getById(id: string): Promise<Client | undefined> {
        return Promise.resolve(this.clients.get(id));
    }

    store(entity: Client): Promise<Client> {
        this.clients.set(entity.id?.toString(), entity);
        return Promise.resolve(entity);
    }

    update(entity: Client): Promise<Client | undefined> {
        this.clients.set(entity.id?.toString(), entity);
        return Promise.resolve(entity);
    }   

    delete(id: string): Promise<boolean> {
        return Promise.resolve(this.clients.delete(id));
    }
}

export default new ClientRepository();