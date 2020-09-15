var grpc = require('grpc');
var { loadProtoFile, findById } = require('./controllers/product.controller');

/**
 * Starts an RPC server that receives requests for the Greeter service at the
 * sample server port
 */
function main() {

    // Instantiate the server 
    var server = new grpc.Server();
    var protofile = loadProtoFile();
    server.addService(protofile.ProductEndPoint.service, {findById});
    server.bind('0.0.0.0:50051', grpc.ServerCredentials.createInsecure());
    server.start();
}

main();