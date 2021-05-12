import {bus2} from "../../main";

export { Api };

class Api {
  static token;

  static get baseUrl() {
    return 'http://localhost:8080/api';
  }

  static get timeout() {
    return 60 * 1000;
  }

  static async fetch(url, secure, init = {}, controller) {
    if (secure && Api.token) {
      if (!init.headers)
        init.headers = {};

      init.headers['Authorization'] = `bearer ${Api.token}`;
    }

    controller = controller || new AbortController();
    const timer = setTimeout(() => controller.abort(), Api.timeout);
    init.signal = controller.signal

    try {
      const response = await fetch(url, init);
      const text = await response.text();
      const result = text ? (JSON).parse(text) : {};
      if (result.code)
        throw result;

      return result;
    } catch (error) {
      bus2.$emit('error', error.code)
      if (!error.code) {
        // eslint-disable-next-line no-ex-assign
        error = { "code": 99, "description": error.message.toLowerCase() };
      }
      throw error;
    } finally {
      clearTimeout(timer);
    }
  }

  static async get(url, secure, controller) {
    return await Api.fetch(url, secure, {}, controller)
  }

  static async post(url, secure, data, controller) {
    return await Api.fetch(url, secure, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=utf-8'
      },
      body: JSON.stringify(data)
    }, controller);
  }

  static async put(url, secure, data, controller) {
    return await Api.fetch(url, secure,{
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json; charset=utf-8'
      },
      body: JSON.stringify(data)
    }, controller);
  }

  static async delete(url, secure, controller) {
    return await Api.fetch(url, secure, {
      method: 'DELETE',
    }, controller);
  }

  static saveToken() {
    localStorage.setItem('token', this.token);
  }

  static restoreToken() {
    let token = localStorage.getItem('token');
    if (token)
      this.token = token;
  }

  static deleteToken() {
    this.token = null;
    localStorage.removeItem('token');
  }
}