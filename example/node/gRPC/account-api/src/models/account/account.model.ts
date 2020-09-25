import { Account } from './account.interface';
import { Model } from "../model.interface";


export function accountMocks() {
    return new Map([
        ['1', { id: '1', balance: 200.00 }]
    ]);
}

export class AccountModel implements Model<Account> {

    private accounts: Map<string, Account> = accountMocks();

    getAll(): Promise<Account[]> {
        return Promise.resolve(Array.from(this.accounts.values()));
    }

    getById(id: string): Promise<Account | undefined> {
        return Promise.resolve(this.accounts.get(id));
    }

    store(entity: Account): Promise<Account> {
        this.accounts.set(entity.id, entity);
        return Promise.resolve(entity);
    }

    update(entity: Account): Promise<Account | undefined> {
        this.accounts.set(entity.id, entity);
        return Promise.resolve(entity);
    }   

    delete(id: string): Promise<boolean> {
        return Promise.resolve(this.accounts.delete(id));
    }

}