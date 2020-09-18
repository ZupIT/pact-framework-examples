var grpc = require('@grpc/grpc-js');
var protoLoader = require('@grpc/proto-loader');

var PROTO_PATH = __dirname + '/../../../pb/products.proto';

export function loadProtoFile() {
    var packageDefinition = protoLoader.loadSync(
        PROTO_PATH,
        {keepCase: true,
         longs: String,
         enums: String,
         defaults: true,
         oneofs: true
        });
    
    // Load proto file
    return grpc.loadPackageDefinition(packageDefinition).grpcproduct;
}

/**
 * Implements the findById RPC method.
 */
export function findById(call: any, callback: any) {
    callback(null, {
        product: {
            id: call.request.value ,
            name: `product ${call.request.value}`
        }
    });
}