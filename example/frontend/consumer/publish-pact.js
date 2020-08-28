const { Publisher } = require('@pact-foundation/pact-node');
const path = require('path');

const publisher = new Publisher({
  pactBroker: process.argv[2],
  pactFilesOrDirs: [path.resolve(process.cwd(), 'pact')],
  consumerVersion: process.version,
});

publisher.publish()
  .then(() => console.log('Pact contract publishing complete!'))
  .catch(err => console.log(`Error on publishing Pact contract ${err}`));
