import cors from 'cors';
import express from 'express';
import { APP_PORT, APP_URL } from './constants';
import routes from './routes';

const app = express();

app.listen(APP_PORT, () => console.log(`[ACCOUNT API] Running on ${APP_URL}`));

app.use(express.json());
app.use(cors());
app.use(routes);
