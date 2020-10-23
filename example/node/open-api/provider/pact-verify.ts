import { spawn } from "child_process";
import axios from 'axios';

const SUCCESS = 0;
const ERROR = 1;

const ls = spawn("swagger-mock-validator", [
    "./doc/swagger.json",
    "http://localhost:9292",
    "--provider",
    "AccountApi"
]);

ls.stdout.on("data", data => {
    console.log(''+data);
});

ls.on("close", code => {
    if (code === SUCCESS) {
        console.log('VALIDATION SUCCESS');
        // axios.post(http://localhost:9292/pacts/provider/AccountApi/consumer/ClientApi/pact-version/a298328437d6f301c094cabdfcb56d9c9c78be50/verification-results);
        // {
        //     "success": true,
        //     "providerApplicationVersion": "4.5.6",
        //     "buildUrl": "http://my-ci.org/build/3456"
        // }
    }

    if (code === ERROR) {
        console.log('VALIDATION ERROR');
    }
});