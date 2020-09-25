import { credentials, loadPackageDefinition } from '@grpc/grpc-js';
import { loadSync } from '@grpc/proto-loader';
import { Router, Request, Response } from 'express';

const PROTO_PATH = __dirname + '../../../../pb/products.proto';

function createClient(packageName:string, serviceName: string) {
    const PACKAGE_DEFINITION = loadSync(
        PROTO_PATH,
        {keepCase: true,
         longs: String,
         enums: String,
         defaults: true,
         oneofs: true
        });
      
      // Load proto file
      var product_proto: any = loadPackageDefinition(PACKAGE_DEFINITION)[packageName];
  
      // generate credentials according to your Authority provider
      var newCredentials = credentials.createInsecure();
  
      // Stablish connection
      return new product_proto[serviceName]('localhost:50051', newCredentials);
}


const routes = Router();

routes.post('/grpc/*', async (req: Request, res: Response) => {
    
    // translate http to gRPC request
    const [ service, method ] = req.path.split('/grpc/')[1].split('/');

    const packageName = service.slice(0, service.lastIndexOf('.'));
    const serviceName = service.slice(service.lastIndexOf('.'));

    // createCliente
    const client = createClient(packageName, serviceName);

    client[method](req.params, (err: any, response: any) => err ? res.status(500) : res.status(200).json(response));

});

export default routes;
