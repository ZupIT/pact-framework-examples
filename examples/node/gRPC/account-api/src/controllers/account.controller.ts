import { Account } from '../repositories/account/account.interface';
import { AccountRepository } from '../repositories/account/account.repository';

export class AccountController {

    accountRepository = new AccountRepository();

    findById = async (call: any, callback: any) => {
        const account: Account | undefined = await this.accountRepository.getById(call.request.accountId);
        let error = null;

        if (!account) {
            error = new Error('Account not found');
        }

        callback(error, { accountId: account?.id, balance: account?.balance });
    }

    getAll = async (call: any, callback: any) => {
        const account: Account[] | undefined = await this.accountRepository.getAll();
        callback(null, 
            account.map(({id, balance, accountType }: Account) => { 
                return {accountId: id, balance, accountType} }
            )
        );
    }
}