import { Api } from './api.js';

export { categoryApi };

class categoryApi {
    static get url() {
        return `${Api.baseUrl}/categories`;
    }

    static async add(category, controller) {
        const result= await Api.post(categoryApi.url, true, category, controller);
        if(result.id){
            // const r1 = await categoryApi.getAll(null);
            console.log("Se crea la cat");
        }else{
            console.log("No se podo agregar la categoria");
        }
    }

    static async modify(category, controller) {
        const result= await Api.put(`${categoryApi.url}/${category.id}`, true, category, controller);
        if(result.id){
            console.log("Se logro moficar la categoria ");
            console.log(category);
        }
    }

    static async delete(categoryID, controller) {
        return await Api.delete(`${categoryApi.url}/${categoryID}`, true, controller);
    }

    static async get(categoryID, controller) {
        return await Api.get(`${categoryApi.url}/${categoryID}`,true,  controller);
    }

    static async getAll(controller) {
        return await Api.get(categoryApi.url, true, controller);
    }
}


