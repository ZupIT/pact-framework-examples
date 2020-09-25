import { loadPackageDefinition } from '@grpc/grpc-js';
import { loadSync } from '@grpc/proto-loader';

export function loadProtoFile(protoPath: string) {
    var packageDefinition = loadSync(
        protoPath,
        {keepCase: true,
         longs: String,
         enums: String,
         defaults: true,
         oneofs: true
        });
    
    // Load proto file
    return loadPackageDefinition(packageDefinition);
}