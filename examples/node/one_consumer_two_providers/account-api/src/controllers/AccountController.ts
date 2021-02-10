import { Request, Response } from 'express';
import { AccountRepository } from '../repositories/AccountRepository';

export class AccountController {
  public getAccountWithBalance(req: Request, res: Response): Response {
    const { clientID } = req.params;
    const accountRepository = new AccountRepository();

    try {
      const account = accountRepository.getAccountByClientID(Number(clientID));

      return res.status(200).json(account);
    } catch (err) {
      return res.status(404).json({ message: err.message });
    }
  }
}
