import { Api } from './api.js';

export { routineApi, Exercise };

class routineApi {
    static get url() {
        return `${Api.baseUrl}/routines`;
    }

    static async add(routine, controller) {
        console.log(routine);
        const result= await Api.post(routineApi.url, true, routine, controller);
        if(result.id){
            const r1 = await routineApi.getAll(null);
            console.log(r1);
        }else{
            console.log("No se podo agregar la rutina");
        }
    }

    static async modify(routine, controller) {
        const result= await Api.put(`${routineApi.url}/${routine.id}`, true, routine, controller);
        if(result.id){
            console.log("Se logro moficar la rutina ");
            console.log(routine);
        }
    }

    static async delete(routineID, controller) {
        return await Api.delete(`${routineApi.url}/${routineID}`, true, controller);
    }

    static async get(routineID, controller) {
        return await Api.get(`${routineApi.url}/${routineID}`,true,  controller);
    }

    static async getAll(controller) {
        return await Api.get(routineApi.url, true, controller);
    }
}

class Exercise {
    constructor(id, name, detail) {
        if (id) {
            this.id = id;
        }
        this.name = name;
        this.detail = detail;
    }
}
