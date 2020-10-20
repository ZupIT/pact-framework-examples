import { Account } from '../repositories/account/account.interface';
import { AccountRepository } from '../repositories/account/account.repository';
import {GET, Path, PathParam} from 'typescript-rest';


@Path('/balance')
class AccountController {

    accountRepository = new AccountRepository();

    @Path(':accountID')
    @GET
    async findById(@PathParam('accountID') accountID: string): Promise<Account | Error> {
        const account: Account | undefined = await this.accountRepository.getById(accountID);

        if (!account) {
            return new Error('Account not found');
        }

        return account;
    }

    @GET
    getAll(): Promise<Account[]> {
        return this.accountRepository.getAll();
    }

}