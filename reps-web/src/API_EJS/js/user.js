import { Api } from './api.js';
import { router } from '../../main';
import state from "../../store/state";

export { UserApi, Credentials };
let registeredUsr = 0;
class UserApi {
    static get url() {
        return `${Api.baseUrl}/users`;
    }


    static async login(credentials, controller) {
        console.log(credentials);
        const result = await Api.post(`${UserApi.url}/login`, false, credentials, controller);
        Api.token = result.token;
        if (Api.token){
            state.token = Api.token;
            await router.push('/MisRutinas');
        }
    }

    getRegisteredUsr(){
        return registeredUsr;
    }

    static async verifyCode(credentials,controller){
        const result = await Api.post(`${UserApi.url}/verify_email`, false, credentials, controller);
        if(result.code){
            console.log("Error");
        }else{

            // await router.push('/MisRutinas');
        }
    }

    static async resendCode(credentials,controller){
        await Api.post(`${UserApi.url}/resend_verification`, false, credentials, controller);
    }

    static async register(credentials, controller) {
        console.log(credentials);
        const result = await Api.post(`${UserApi.url}`, false, credentials, controller);
        if(result.id){
            console.log("Se creo el usr. El resultado obtenido fue: ");
            console.log(result);
            registeredUsr = 1;
            await router.push('/ConfirmacionMail');
        }
    }

    static async logout(controller) {
        await Api.post(`${UserApi.url}/logout`, true, controller);
        Api.token = undefined;
    }
}

class Credentials {
    constructor(username, password) {
        this.username = username;
        this.password = password;
    }
}