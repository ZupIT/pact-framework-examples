import { Publisher } from '@pact-foundation/pact';
import path from 'path';
import { PACT_BROKER_URL } from '../constants';

const publish = new Publisher({
  pactBroker: PACT_BROKER_URL,
  pactFilesOrDirs: [path.resolve(process.cwd(), 'pacts')],
  consumerVersion: '1.0.0',
});

publish
  .publishPacts()
  .then(() => console.log('Pact contract publishing complete!'))
  .catch(err => console.log(`Error on publishing Pact contract ${err}`));
