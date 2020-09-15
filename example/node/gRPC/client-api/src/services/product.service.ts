var PROTO_PATH = __dirname + '/../../../pb/products.proto';

var grpc = require('grpc');
var protoLoader = require('@grpc/proto-loader');
var packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    });

    
export default function findProductById(id: number) {
  // Load proto file
  var product_proto = grpc.loadPackageDefinition(packageDefinition).grpcsample.product;

  // Stablish connection
  var client = new product_proto.ProductEndPoint('localhost:50051', grpc.credentials.createInsecure());

  // Execute Remote Procedure Call
  return new Promise((resolve, reject) => {
    client.findById({value: `${id}`}, function(err: any, response: any) {
      if (err) {
        reject(err);
      } else {
        resolve(response);
      }
    });
  });

}