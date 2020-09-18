import { ClientOptions, credentials, loadPackageDefinition } from '@grpc/grpc-js';
import { loadSync } from '@grpc/proto-loader';

const PROTO_PATH = __dirname + '/../../../pb/products.proto';

const PACKAGE_DEFINITION = loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    });


export function findProductById(id: number, options?: ClientOptions) {
  // Load proto file
  var product_proto: any = loadPackageDefinition(PACKAGE_DEFINITION).grpcproduct;

  // generate credentials according to your Authority provider
  var newCredentials = credentials.createInsecure();

  // Stablish connection
  var client: any = new product_proto.ProductEndPoint('localhost:50051', newCredentials, options);

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