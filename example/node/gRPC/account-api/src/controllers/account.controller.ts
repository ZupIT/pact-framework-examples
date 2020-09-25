import { AccountModel } from './../models/account/account.model';

export class AccountController {

    private accountModel = new AccountModel();

    findById = (call: any, callback: any) => {
        callback(null, {
            balance_response: this.accountModel.getById(call.request.accountId)
        });
    }

    getAll = (call: any, callback: any) => {
        callback(null, {
            balance_response: this.accountModel.getAll()
        });
    }

}