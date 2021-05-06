import { Api } from './api.js';

export { SportApi, Sport };

class SportApi {
  static get url() {
    return `${Api.baseUrl}/sports`;
  }

  static async add(sport, controller) {
   return await Api.post(SportApi.url, true, sport, controller);
  }

  static async modify(sport, controller) {
    return await Api.put(`${SportApi.url}/${sport.id}`, true, sport, controller);
  }

  static async delete(id, controller) {
    return await Api.delete(`${SportApi.url}/${id}`, true, controller);
  }

  static async get(id, controller) {
    return await Api.get(`${SportApi.url}/${id}`,true,  controller);
  }

  static async getAll(controller) {
    return await Api.get(SportApi.url, true, controller);
  }
}

class Sport {
  constructor(id, name, detail) {
    if (id) {
      this.id = id;
    }
    this.name = name;
    this.detail = detail;
  }
}
