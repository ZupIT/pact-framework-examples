import { Product } from "../models/product";

export const makeProducts = (): any => {
  return new Map([
    ["09", new Product(9, "CREDIT_CARD", "Gem Visa")],
    ["10", new Product(10, "CREDIT_CARD", "28 Degrees")],
    ["11", new Product(11, "PERSONAL_LOAN", "MyFlexiPay")],
  ]);
}