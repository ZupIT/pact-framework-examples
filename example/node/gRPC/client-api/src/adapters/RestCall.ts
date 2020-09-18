import { CallCredentials, Metadata, status } from "@grpc/grpc-js";
import { Call, CallStreamOptions, Deadline, InterceptingListener, MessageContext } from "@grpc/grpc-js/build/src/call-stream";
import { ChannelImplementation } from "@grpc/grpc-js/build/src/channel";

export class RestCall implements Call {

    private listener: any;
    
    constructor(
      methodName: string,
      channel: ChannelImplementation,
      options: CallStreamOptions,
      callNumber: number) {}
  
    cancelWithStatus(status: status, details: string): void {
      throw new Error('Method not implemented.');
    }
    getPeer(): string {
      throw new Error('Method not implemented.');
    }
    start(metadata: Metadata, listener: InterceptingListener): void {
      this.listener = listener;
      console.log('METADATA');
      console.log(listener);
    }
    sendMessageWithContext(context: MessageContext, message: Buffer): void {
      // throw new Error('Method not implemented.');
      console.log('MESSAGE CONTEXT');
      console.log(message);
  
    }
    startRead(): void {
      throw new Error('Method not implemented.');
    }
    halfClose(): void {
      throw new Error('Method not implemented.');
    }
    getDeadline(): Deadline {
      throw new Error('Method not implemented.');
    }
    getCredentials(): CallCredentials {
      throw new Error('Method not implemented.');
    }
    setCredentials(credentials: CallCredentials): void {
      throw new Error('Method not implemented.');
    }
    getMethod(): string {
      throw new Error('Method not implemented.');
    }
    getHost(): string {
      throw new Error('Method not implemented.');
    }
  }