import { Account } from '../repositories/account/account.interface';
import { AccountRepository } from '../repositories/account/account.repository';
import { GET, POST, Path, PathParam, Errors, Param } from 'typescript-rest';
import { Produces, Response } from 'typescript-rest-swagger';

@Path('/account')
@Produces("application/json; charset=utf-8")
class AccountController {

    accountRepository = new AccountRepository();

    @Path(':accountID')
    @Response<Account>(200, 'Retrieve an account information')
    @Response(404, 'Account not found')
    @GET
    async findById(@PathParam('accountID') accountID: string): Promise<Account> {
        const account: Account | undefined = await this.accountRepository.getById(accountID);
        
        if (!account) {
            throw new Errors.NotFoundError('Account not found');
        }

        return account;
    }

    @GET
    async getAll(): Promise<Account[]> {
        return this.accountRepository.getAll();
    }

}