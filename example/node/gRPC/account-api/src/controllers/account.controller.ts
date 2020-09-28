import { Account } from '../repositories/account/account.interface';
import AccountRepository from '../repositories/account/account.repository';

export class AccountController {

    findById = async (call: any, callback: any) => {
        const account: Account | undefined = await AccountRepository.getById(call.request.accountId);
        console.log(call.request.accountId);
        callback(null, { accountId: account?.id, balance: account?.balance });
    }

    getAll = async (call: any, callback: any) => {
        const account: Account[] | undefined = await AccountRepository.getAll();
        callback(null, 
            account.map(({id, balance, accountType }: Account) => { 
                return {accountId: id, balance, accountType} }
            )
        );
    }
}