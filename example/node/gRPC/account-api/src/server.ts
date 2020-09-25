import { GRPC_SERVER_URL } from './constants';
import { Server, ServerCredentials } from '@grpc/grpc-js';
import { AccountController } from './controllers/account.controller';
import { loadProtoFile } from './proto-loader';

const ACCOUNT_CONTROLLER: AccountController = new AccountController();

/**
 * Starts an RPC server that receives requests for the Greeter service at the
 * sample server port
 */
function main() {

    // Instantiate the server 
    var server = new Server();

    var protofile: any = loadProtoFile(__dirname + '/protos/AccountResource.proto');
    server.addService(protofile.br.com.zup.pact.provider.resource.AccountResource.service, { 
        getBalanceByClientId: ACCOUNT_CONTROLLER.findById,
        getAll: ACCOUNT_CONTROLLER.getAll
     });
   
    server.bindAsync(GRPC_SERVER_URL, ServerCredentials.createInsecure(), () => {
        server.start();
    });
}

main();