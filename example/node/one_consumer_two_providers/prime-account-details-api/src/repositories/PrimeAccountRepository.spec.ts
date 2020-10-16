import { EXISTENT_CLIENT_ID, NON_EXISTENT_CLIENT_ID } from '../constants';
import { PrimeAccountRepository } from './PrimeAccountRepository';

jest.mock('axios');

describe('PrimeAccountRepository', () => {
  let primeAccountRepository: PrimeAccountRepository;

  beforeAll(() => {
    primeAccountRepository = new PrimeAccountRepository();
  });

  it('should get prime account when entering an existing ID', () => {
    const primeAccountMock = {
      clientID: 1,
      isPrime: true,
      discountPercentageFee: 2,
    };

    const primeAccount = primeAccountRepository.getPrimeAccountByClientID(
      EXISTENT_CLIENT_ID,
    );

    expect(primeAccount).toEqual(primeAccountMock);
  });

  it('should get an error when entering a non-existing ID', () => {
    expect(() =>
      primeAccountRepository.getPrimeAccountByClientID(NON_EXISTENT_CLIENT_ID),
    ).toThrowError("Prime Account doesn't exist");
  });
});
