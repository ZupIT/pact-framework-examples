import { createGrpcServer } from "./gRPCServer";

/**
 * Starts an RPC server that receives requests for the Greeter service at the
 * sample server port
 */
function main() {
    createGrpcServer();
}

main();