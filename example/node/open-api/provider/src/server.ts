import * as express from 'express';
import * as cors from 'cors';
import { APP_PORT, APP_URL } from './constants';
import { Server } from 'typescript-rest';


const app = express();
app.use(express.json());
app.use(cors());

Server.loadServices(app, 'controllers/*', __dirname);
Server.swagger(app, { 
    endpoint: 'api-docs',
    filePath: './doc/swagger.json',
    host: APP_URL,
    schemes: ['http'],
    swaggerUiOptions: {
        modelPropertyMacro: (teste: any) => 'console.log(teste)'
    }
});

app.listen(APP_PORT, () => console.log(`[ACCOUNT API] Running on ${APP_URL}`));