import { PrimeAccountService } from './PrimeAccountService';

jest.mock('axios');

describe('AccountService', () => {
  let primeAccountService: PrimeAccountService;

  beforeAll(() => {
    primeAccountService = new PrimeAccountService();
  });

  it('should get prime account when entering an existing ID', () => {
    const primeAccountMock = {
      clientID: 1,
      isPrime: true,
      discountPercentageFee: 2,
    };

    const primeAccount = primeAccountService.getPrimeAccountByClientID(1);

    expect(primeAccount).toEqual(primeAccountMock);
  });

  it('should get an error when entering a non-existing ID', () => {
    expect(() =>
      primeAccountService.getPrimeAccountByClientID(9124214),
    ).toThrowError("Prime Account doesn't exist");
  });
});
