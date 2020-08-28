const { Verifier } = require("@pact-foundation/pact");
const controller = require("./product.controller");
const Product = require("./product");
const express = require("express");
const authMiddleware = require("../middleware/auth.middleware");
const routes = require("./product.routes");

describe("Pact Verification", () => {
  let app;

  beforeAll(async () => {
    app = express()
      .use(routes)
      .listen(3333, () => console.log("Provider listening on port 3333"));
  });

  it("validates the expectations of ProductService", () => {
    const opts = {
      logLevel: "INFO",
      providerBaseUrl: "http://localhost:3333",
      provider: "NodeProductApi",
      providerVersion: "1.0.0",
      pactBrokerUrl: "http://localhost:9292",
      publishVerificationResult: true,
      stateHandlers: {
        "product with ID 10 exists": () => {
          controller.repository.products = new Map([
            ["10", new Product(10, "CREDIT_CARD", "28 Degrees", "v1")],
          ]);
        },
        "products exist": () => {
          controller.repository.products = new Map([
            ["09", new Product(9, "CREDIT_CARD", "Gem Visa", "v1")],
            ["10", new Product(10, "CREDIT_CARD", "28 Degrees", "v1")],
          ]);
        },
        "no products exist": () => {
          controller.repository.products = new Map();
        },
        "product with ID 11 does not exist": () => {
          controller.repository.products = new Map();
        },
      },
      requestFilter: (req, res, next) => {
        if (!req.headers["authorization"]) {
          next();
          return;
        }
        req.headers["authorization"] = `Bearer ${new Date().toISOString()}`;
        next();
      },
    };

    return new Verifier(opts)
      .verifyProvider()
      .then((output) => {
        console.log(output);
      })
      .finally(() => app.close());
  });
});
