import { Account } from './account.interface';
import { Repository } from "../repository.interface";


export function accountMocks() {
    return new Map([
        ['15', { id: 15 , balance: 200.00, accountType: 'checking' }]
    ]);
}

export class AccountRepository implements Repository<Account> {

    private accounts: Map<string, Account> = accountMocks();

    setAccounts(accounts: Map<string, Account>) {
        this.accounts = accounts;
    }

    getAll(): Promise<Account[]> {
        return Promise.resolve(Array.from(this.accounts.values()));
    }

    getById(id: string): Promise<Account | undefined> {
        return Promise.resolve(this.accounts.get(`${id}`));
    }

    store(entity: Account): Promise<Account> {
        this.accounts.set(entity.id.toString(), entity);
        return Promise.resolve(entity);
    }

    update(entity: Account): Promise<Account | undefined> {
        this.accounts.set(entity.id.toString(), entity);
        return Promise.resolve(entity);
    }   

    delete(id: string): Promise<boolean> {
        return Promise.resolve(this.accounts.delete(id));
    }

}

export default new AccountRepository();