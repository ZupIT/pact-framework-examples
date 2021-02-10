import { Request, Response } from 'express';
import { PrimeAccountRepository } from '../repositories/PrimeAccountRepository';

export class PrimeAccountController {
  public getPrimeAccount(req: Request, res: Response): Response {
    const { clientID } = req.params;
    const primeAccountRepository = new PrimeAccountRepository();

    try {
      const primeAccount = primeAccountRepository.getPrimeAccountByClientID(
        Number(clientID),
      );

      return res.status(200).json(primeAccount);
    } catch (err) {
      return res.status(404).json({ message: err.message });
    }
  }
}
