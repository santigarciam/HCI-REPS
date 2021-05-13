import { Api } from './api.js';

export { FavApi, };

class FavApi {
    static get url() {
        return `${Api.baseUrl}/favourites`;
    }

    static async getFavourites(controller) {
        return await Api.get(FavApi.url, true, controller);
    }

    static async add(id, controller) {
        return await Api.post(`${FavApi.url}/${id}`, true, controller);
    }
    static async delete(id, controller) {
        return await Api.post(`${FavApi.url}/${id}`, true, controller);
    }

}