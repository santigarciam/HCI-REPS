import { Api } from './api.js';

export { cycleExercisesApi };

class cycleExercisesApi {
    static get url() {
        return `${Api.baseUrl}/cycles`;
    }

    static async add(cycleID,exerciseID,data, controller) {
        // console.log(cycle);
        const result= await Api.post(`${cycleExercisesApi.url}/${cycleID}/exercises/${exerciseID}`, true, data, controller);
        if(result.order){
            // const r1 = await cycleApi.getAll(null);
            // console.log(r1);
        }else{
            console.log("---------------------------------");
            console.log(result);
            console.log("No se podo agregar el ejercicio al ciclo");
        }
        return result;
    }

    static async modify(cycleID,exerciseID,data, controller){
        const result= await Api.put(`${cycleExercisesApi.url}/${cycleID}/exercises/${parseInt(exerciseID)}`, true, data, controller);
        if(!result.id){
            console.log("NO se logro moficar ej del ciclo ");
            console.log(result);

            // console.log(cycle);
        }
    }

    static async delete(cycleID,exerciseID, controller){
        return await Api.delete(`${cycleExercisesApi.url}/${cycleID}/exercises/${exerciseID}`, true, controller);
    }

    static async get(cycleID,exerciseID, controller){
        return await Api.get(`${cycleExercisesApi.url}/${cycleID}/exercises/${exerciseID}`,true,  controller);
    }

    static async getAll(cycleID,controller) {
        return await Api.get(`${cycleExercisesApi.url}/${cycleID}/exercises/`, true, controller);
    }
}