import { Request, Response } from 'express';
import { PrimeAccountService } from '../services/PrimeAccountService';

export class PrimeAccountController {
  public getPrimeAccount(req: Request, res: Response): Response {
    const { clientID } = req.params;
    const primeAccountService = new PrimeAccountService();

    try {
      const primeAccount = primeAccountService.getPrimeAccountByClientID(
        Number(clientID),
      );

      return res.status(200).json(primeAccount);
    } catch (err) {
      return res.status(404).json({ message: err.message });
    }
  }
}
