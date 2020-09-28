import { FullRequester, InterceptorOptions } from "@grpc/grpc-js/build/src/client-interceptors";
import axios from 'axios';

export class HtttpRequester implements Partial<FullRequester> {

    private options: InterceptorOptions;
    private mockServerBaseUrl: string;

    constructor( mockServerBaseUrl: string,options: InterceptorOptions) {
        this.options = options;
        this.mockServerBaseUrl = mockServerBaseUrl;
    }

    sendMessage = (message: any, next: (message: any) => void ) => {

        const url = `${this.mockServerBaseUrl}/grpc${this.options.method_definition.path}`;

        console.log(`Executing request for ${url}`)

        axios.post(url, message, { headers: { 'Content-Type': 'application/json; charset=utf-8' }, })
        .then(() => next(message))
        .then(() => console.log('GRPC <-> MOCK_SERVER : Success ! '))
        .catch( error => { 
            console.log('GRPC <-> MOCK_SERVER : ERROR ! ');
            console.log(error);
        });
    };
}