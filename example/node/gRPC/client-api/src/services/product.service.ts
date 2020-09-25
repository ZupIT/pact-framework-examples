import { ClientOptions, credentials, loadPackageDefinition } from '@grpc/grpc-js';
import { loadSync } from '@grpc/proto-loader';

export const PROTO_PATH = __dirname + '/../../../pb/products.proto';

export class ProductService {

  options?: ClientOptions = {};
  client: any;

  constructor(options?: ClientOptions) {
    this.options = options;
    this.client = this.createClient();
  }

  async findById(id: string) {

    // Execute Remote Procedure Call
    return new Promise((resolve, reject) => {
      this.client.findById({value: `${id}`}, (err: any, response: any) => err ? reject(err) : resolve(response));
    });
    
  }

  private createClient() {
    const PACKAGE_DEFINITION = loadSync(
      PROTO_PATH,
      {keepCase: true,
       longs: String,
       enums: String,
       defaults: true,
       oneofs: true
      });
    
    // Load proto file
    var product_proto: any = loadPackageDefinition(PACKAGE_DEFINITION).grpcproduct;

    // generate credentials according to your Authority provider
    var newCredentials = credentials.createInsecure();

    // Stablish connection
    return new product_proto.ProductEndPoint('localhost:50051', newCredentials, this.options);
  }

}