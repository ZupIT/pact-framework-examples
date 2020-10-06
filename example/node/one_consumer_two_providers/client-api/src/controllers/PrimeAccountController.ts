import { Request, Response } from 'express';
import { PrimeAccountService } from '../services/PrimeAccountService';

export class PrimeAccountController {
  public async getPrimeAccount(req: Request, res: Response): Promise<Response> {
    const { id } = req.params;

    const primeAccountService = new PrimeAccountService();

    try {
      const primeAccount = await primeAccountService.getPrimeAccountDetailsByClientID(
        Number(id),
      );

      return res.status(200).json(primeAccount);
    } catch (err) {
      return res.status(404).json({ message: err.message });
    }
  }
}
