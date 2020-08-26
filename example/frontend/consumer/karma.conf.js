// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

const { resolve } = require("path");

module.exports = function (config) {
  config.set({
    basePath: "",
    frameworks: ["jasmine", "@angular-devkit/build-angular"],
    plugins: [
      require("karma-jasmine"),
      require("karma-chrome-launcher"),
      require("karma-jasmine-html-reporter"),
      require("karma-coverage-istanbul-reporter"),
      require("@angular-devkit/build-angular/plugins/karma"),
      require("@pact-foundation/karma-pact"),
      // "karma-*",
      // "@pact-foundation/karma-pact",
    ],
    client: {
      clearContext: false, // leave Jasmine Spec Runner output visible in browser
    },
    coverageIstanbulReporter: {
      dir: require("path").join(__dirname, "./coverage/consumer"),
      reports: ["html", "lcovonly", "text-summary"],
      fixWebpackSourcePaths: true,
    },
    reporters: ["progress", "kjhtml"],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ["Chrome"],
    singleRun: false,
    restartOnFileChange: true,

    pact: [
      {
        port: 1234,
        consumer: "Consumer",
        provider: "Provider",
        logLevel: "DEBUG",
        log: resolve(process.cwd(), "logs", "pact.log"),
        dir: resolve(process.cwd(), "pacts"),
      },
    ],

    proxies: {
      "/product-service/": "http://127.0.0.1:1234/product-service/",
    },
  });
};
