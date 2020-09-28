import { ACCOUNTS } from '../constants';

export interface Account {
  clientID: number;
  accountID: number;
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
