import { Product } from '../domain/product';

export const makeFakeProductsTests = (): any => {
  return new Map([
    [1, new Product(1, "CREDIT_CARD", "Gem Visa")],
    [2, new Product(2, "CREDIT_CARD", "28 Degrees")],
    [3, new Product(3, "PERSONAL_LOAN", "MyFlexiPay")],
    [4, new Product(4, "CREDIT_CARD", "MyFlexiPayCredit")],
    [5, new Product(5, "PERSONAL_LOAN", "MyFlexiPay")],
  ]);
}
