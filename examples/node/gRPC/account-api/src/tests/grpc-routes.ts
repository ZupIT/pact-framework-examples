import { GRPC_SERVER_URL } from './../constants';
import { credentials, loadPackageDefinition } from '@grpc/grpc-js';
import { loadSync } from '@grpc/proto-loader';
import { Router, Request, Response } from 'express';

const PROTO_PATH = __dirname + './../protos/AccountResource.proto';

function createClient(protoPath: string, packageName:string, serviceName: string) {
    const PACKAGE_DEFINITION = loadSync(
        protoPath,
        {keepCase: true,
            longs: String,
            enums: String,
            defaults: true,
            oneofs: true
        }
    );
    
    // Load proto file
    const account_proto: any = loadPackageDefinition(PACKAGE_DEFINITION);

    // generate credentials according to your Authority provider
    const newCredentials = credentials.createInsecure();

    // Stablish connection
    const services = packageName.split('.').reduce((p,c)=>p&&p[c]||null, account_proto);

    // Stablish connection
    return new services[serviceName](GRPC_SERVER_URL, newCredentials);
}

const routes = Router();

routes.post('/grpc/*', async (req: Request, res: Response) => {
    
    // translate http to gRPC request
    const [ service, method ] = req.path.split('/grpc/')[1].split('/');

    const packageName = service.slice(0, service.lastIndexOf('.'));
    const serviceName = service.slice(service.lastIndexOf('.')+1);

    // create Account Client
    const client = createClient(PROTO_PATH, packageName, serviceName);
    client[method](req.body, (err: any, response: any) => err ? res.status(500).json(err) : res.status(200).json(response));

});

export default routes;
