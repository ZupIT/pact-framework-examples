import { GRPC_SERVER_URL } from './../constants';
import { ClientOptions, credentials, loadPackageDefinition } from '@grpc/grpc-js';
import { loadSync } from '@grpc/proto-loader';

export const PROTO_PATH = __dirname + './../protos/AccountResource.proto';

export class AccountService {

  options?: ClientOptions = {};
  gRPCClient: any;

  constructor(options?: ClientOptions) {
    this.options = options;
    this.gRPCClient = this.creategRPCClient();
  }

  async getById(id: number) {

    // Execute Remote Procedure Call
    return new Promise((resolve, reject) => {
      this.gRPCClient.findById({accountId: id}, (err: any, response: any) => err ? reject(err) : resolve(response));
    });
    
  }

  private creategRPCClient() {
    const PACKAGE_DEFINITION = loadSync(
      PROTO_PATH,
      {keepCase: true,
       longs: String,
       enums: String,
       defaults: true,
       oneofs: true
      });
    
    // Load proto file
    var product_proto: any = loadPackageDefinition(PACKAGE_DEFINITION);

    // generate credentials according to your Authority provider
    var newCredentials = credentials.createInsecure();

    // Stablish connection
    return new product_proto.br.com.zup.pact.provider.resource.AccountResource(GRPC_SERVER_URL, newCredentials, this.options);
  }

}