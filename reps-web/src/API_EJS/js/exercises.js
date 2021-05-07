import { Api } from './api.js';

export { ExerciseApi, Exercise };

class ExerciseApi {
    static get url() {
        return `${Api.baseUrl}/exercises`;
    }

    static async add(excercise, controller) {
        console.log(excercise);
        const result= await Api.post(ExerciseApi.url, true, excercise, controller);
        if(result.id){
           const r1 = await ExerciseApi.getAll(null);
            console.log(r1);
        }else{
            console.log("No se podo agregar el ejercicio");
        }
    }

    static async modify(excercise, controller) {
        return await Api.put(`${ExerciseApi.url}/${excercise.id}`, true, excercise, controller);
    }

    static async delete(excerciseID, controller) {
        return await Api.delete(`${ExerciseApi.url}/${excerciseID}`, true, controller);
    }

    static async get(excerciseID, controller) {
        return await Api.get(`${ExerciseApi.url}/${excerciseID}`,true,  controller);
    }

    static async getAll(controller) {
        return await Api.get(ExerciseApi.url, true, controller);
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
