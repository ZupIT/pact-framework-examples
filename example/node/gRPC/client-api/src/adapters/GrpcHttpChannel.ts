import { Channel, Deadline } from "@grpc/grpc-js";
import { Call } from '@grpc/grpc-js/build/src/call-stream';
import { RestCall } from "./RestCall";

export class GrpcHttpChannel extends Channel {

    createCall(
        method: string,
        deadline: Deadline,
        host: string | null | undefined,
        parentCall: any,
        propagateFlags: number | null | undefined): Call {
        return new RestCall(method, this, {deadline, host: host || '', flags: propagateFlags || 0, parentCall}, 123);
    }

}