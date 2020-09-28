import { GRPC_SERVER_URL } from './constants';
import { Server, ServerCredentials } from '@grpc/grpc-js';
import { AccountController } from './controllers/account.controller';
import { loadProtoFile } from './proto-loader';

export async function createGrpcServer(): Promise<Server>  {
    const {findById, getAll} = new AccountController();
    const grpcServer = new Server();
  
    var protofile: any = loadProtoFile('src/protos/AccountResource.proto');
    grpcServer.addService(protofile.br.com.zup.pact.provider.resource.AccountResource.service, { 
        findById,
        getAll
    });
  
    return new Promise( (resolve, reject) => {
      grpcServer.bindAsync(GRPC_SERVER_URL, ServerCredentials.createInsecure(), err => {
  
        if (err) reject(err);
  
        grpcServer.start();
        resolve(grpcServer);
      });
    })
  }