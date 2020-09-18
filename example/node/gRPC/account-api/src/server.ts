import { Server, ServerCredentials } from '@grpc/grpc-js';
var { loadProtoFile, findById } = require('./controllers/product.controller');

/**
 * Starts an RPC server that receives requests for the Greeter service at the
 * sample server port
 */
function main() {

    // Instantiate the server 
    var server = new Server();
    var protofile = loadProtoFile();
    server.addService(protofile.ProductEndPoint.service, {findById});
    server.bindAsync('0.0.0.0:50051', ServerCredentials.createInsecure(), () => {
        server.start();
    });
}

main();