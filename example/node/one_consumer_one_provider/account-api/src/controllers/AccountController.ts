import { Request, Response } from 'express';
import { AccountService } from '../services/AccountService';

export class AccountController {
  public getAccountWithBalance(req: Request, res: Response): Response {
    const { clientID } = req.params;
    const accountService = new AccountService();

    try {
      const account = accountService.getAccountByClientID(Number(clientID));

      return res.status(200).json(account);
    } catch (err) {
      return res.status(404).json({ message: err.message });
    }
  }
}
