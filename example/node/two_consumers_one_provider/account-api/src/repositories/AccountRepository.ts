import { ACCOUNTS } from '../constants';

export interface Account {
  clientID: number;
  name: string;
  fantasyName: string;
  balance: number;
}

export class AccountRepository {
  public getAccountByClientID(id: number): Account {
    const account = ACCOUNTS.find(acc => acc.clientID === id);

    if (!account) {
      throw new Error("Account doesn't exist");
    }

    return account;
  }
}
