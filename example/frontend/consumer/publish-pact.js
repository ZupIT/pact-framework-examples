const { Publisher } = require('@pact-foundation/pact-node');
const path = require('path');

const publish = Publisher.create({
  pactBroker: 'http://localhost:9292' ,
  pactFilesOrDirs: [path.resolve(process.cwd(), 'pact')],
  consumerVersion: process.version,
});

publish
  .publish()
  .then(() => console.log('Pact contract publishing complete!'))
  .catch(err => console.log(`Error on publishing Pact contract ${err}`));
