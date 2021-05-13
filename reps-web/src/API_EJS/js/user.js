import { Api } from './api.js';
import {router} from '../../main';
import state from "../../store/state";

export { UserApi, Credentials };

let registeredUsr = 0;

class UserApi {
    static get url() {
        return `${Api.baseUrl}/users`;
    }

    static async getRoutines(parameters, controller){
        return await Api.get(`${UserApi.url}/current/routines/?${parameters}`,true, controller);
    }

    static async login(credentials, controller) {
        console.log(credentials);
        const result = await Api.post(`${UserApi.url}/login`, false, credentials, controller);
        Api.token = result.token;
        if (Api.token){
            state.token = Api.token;
            Api.saveToken();
            await router.push('/MisRutinas');
        }
    }

    static async getCurrentUser(controller){
        const result = await Api.get(`${UserApi.url}/current`,true, controller);
        if (!result.id){
            console.log("ERROR!"); //ERROR
        }
        return result;
    }

    /*static async getAllUsers(controller){
        const result = await Api.get(`${UserApi.url}`,true, controller);
        return result;
    }*/


    getRegisteredUsr(){
        return registeredUsr;
    }

    static async verifyCode(credentials,controller){
        const result = await Api.post(`${UserApi.url}/verify_email`, false, credentials, controller);
        if(result.code){
            console.log("Error");
            return "failed"
        }else{
            console.log("else")
            return "verified"
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
            // await this.$store.dispatch('changeCardID');
        }
        return result;
    }

    static async logout(controller) {
        await Api.post(`${UserApi.url}/logout`, true, controller);
        Api.deleteToken();
        state.token = null;
    }

    static async modifyUserInformation(data, controller){
        const result = await Api.put(`${UserApi.url}/current`, true, data, controller);
        if (!result.id){
            console.log("ERROR!"); //ERROR
        }
    }
}

class Credentials {
    constructor(username, password) {
        this.username = username;
        this.password = password;
    }
}