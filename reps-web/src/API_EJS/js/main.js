import { UserApi, Credentials } from './user.js';
import { SportApi, Sport } from './sport.js';

let currentSport;
let loginButton, logoutButton;
let createButton, updateButton, deleteButton, getButton, getAllButton, abortButton;
let resultTextArea;
let abortController;

function toggleButtons(disabled) {
  createButton.disabled = disabled;
  updateButton.disabled = !disabled;
  deleteButton.disabled = !disabled;
  getButton.disabled = !disabled;
}

function toggleSecurityButtons(disabled) {
  loginButton.disabled = disabled;
  logoutButton.disabled = !disabled;
  createButton.disabled = !disabled;
  getAllButton.disabled = !disabled;
}

function showResult(result) {
  resultTextArea.value = JSON.stringify(result, null, 2)
}

function showError(error) {
  resultTextArea.value = `Request failed: ${error.description} [Code: ${error.code}]`;
}

window.addEventListener('load', () => {
  loginButton = document.querySelector('#login');
  logoutButton = document.querySelector('#logout');

  createButton = document.querySelector('#create');
  updateButton = document.querySelector('#update');
  deleteButton = document.querySelector('#delete');
  getButton = document.querySelector('#get');
  getAllButton = document.querySelector('#getAll');
  abortButton = document.querySelector('#abort');
  resultTextArea = document.querySelector('#result');

  loginButton.addEventListener('click', async () => {
    try {
      const credentials = new Credentials('johndoe', '1234567890');
      await UserApi.login(credentials);
      resultTextArea.value = "";
      toggleSecurityButtons(true);
    } catch (error) {
      showError(error);
    }
  }, false);

  logoutButton.addEventListener('click', async () => {
    try {
      await UserApi.logout();
      resultTextArea.value = "";
      toggleSecurityButtons(false);
      updateButton.disabled = true;
      deleteButton.disabled = true;
      getButton.disabled = true;
    } catch (error) {
      showError(error);
    }
  }, false);

  createButton.addEventListener('click', async () => {
    const index = Math.floor(Math.random() * (999 - 1) + 1)
    const sport = new Sport(null, `sport ${index}`, `sport ${index}`);

    try {
      currentSport = await SportApi.add(sport);
      showResult(currentSport);
      toggleButtons(true);
    } catch (error) {
      showError(error);
    }
  }, false);

  updateButton.addEventListener('click', async () => {
    const index = Math.floor(Math.random() * (999 - 1) + 1)
    currentSport.detail = `sport ${index}`;

    try {
      currentSport = await SportApi.modify(currentSport);
      showResult(currentSport);
    } catch(error) {
      showError(error);
    }
  }, false);

  deleteButton.addEventListener('click', async () => {
    try {
      const result = await SportApi.delete(currentSport.id);
      toggleButtons(false);
      showResult(result);
    } catch(error) {
      showError(error);
    }
  }, false);

  getButton.addEventListener('click', async () => {
    abortButton.disabled = false;
    abortController = new AbortController();

    try {
      let result = await SportApi.get(currentSport.id);
      showResult(result);
      abortButton.disabled = true;
    } catch(error) {
      showError(error);
      abortButton.disabled = true;
    }
  }, false);

  getAllButton.addEventListener('click', async () => {
    abortButton.disabled = false;
    abortController = new AbortController();

    try {
      let result = await SportApi.getAll(abortController);
      showResult(result);
      abortButton.disabled = true;
    } catch(error) {
      showError(error);
      abortButton.disabled = true;
    }
  }, false);

  abortButton.addEventListener('click', () => {
    if (abortController)
      abortController.abort();
  }, false);

}, false);