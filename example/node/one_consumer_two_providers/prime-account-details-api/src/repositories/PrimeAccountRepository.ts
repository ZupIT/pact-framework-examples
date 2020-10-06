import { PRIME_ACCOUNTS } from '../constants';

export interface PrimeAccount {
  clientID: number;
  isPrime: boolean;
  discountPercentageFee: number;
}

export class PrimeAccountRepository {
  public getPrimeAccountByClientID(id: number): PrimeAccount {
    const primeAccount = PRIME_ACCOUNTS.find(prime => prime.clientID === id);

    if (!primeAccount) {
      throw new Error("Prime Account doesn't exist");
    }

    return primeAccount;
  }
}
