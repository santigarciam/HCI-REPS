import { Api } from './api.js';

export { cycleApi };

class cycleApi {
    static get url() {
        return `${Api.baseUrl}/routines`;
    }

    static async add(routineId,cycle, controller) {
        //console.log(cycle);
        const result= await Api.post(`${cycleApi.url}/${routineId}/cycles`, true, cycle, controller);
        if(result.id){
            // const r1 = await cycleApi.getAll(null);
            // console.log(r1);
        }else{
            console.log("---------------------------------");
            console.log(result);
            console.log("No se podo agregar el ciclo");
        }
        return result;
    }

    static async modify(routineId,cycle, controller) {
        const result= await Api.put(`${cycleApi.url}/${routineId}/cycles/${cycle.id}`, true, cycle, controller);
        if(result.id){
            console.log("Se logro moficar el ciclo ");
            console.log(cycle);
        }
    }

    static async delete(routineId,cycle, controller){
        return await Api.delete(`${cycleApi.url}/${routineId}/cycles/${cycle.id}`, true, controller);
    }

    static async get(routineId,cycle, controller){
        return await Api.get(`${cycleApi.url}/${routineId}/cycles/${cycle.id}`,true,  controller);
    }

    static async getAll(routineId,controller) {
        return await Api.get(`${cycleApi.url}/${routineId}/cycles/`, true, controller);
    }
}