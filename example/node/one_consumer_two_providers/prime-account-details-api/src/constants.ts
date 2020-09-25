export const APP_PORT = 3335;

export const APP_URL = `http://localhost:${APP_PORT}`;

export const PACT_BROKER_URL = 'http://localhost:9292';

export const PRIME_ACCOUNTS = [
  {
    clientID: 1,
    isPrime: true,
    discountPercentageFee: 2,
  },
  {
    clientID: 2,
    isPrime: false,
    discountPercentageFee: 5.2,
  },
  {
    clientID: 3,
    isPrime: true,
    discountPercentageFee: 3.1,
  },
];
