import {routineApi} from '../API_EJS/js/routines';
import {ExerciseApi} from '../API_EJS/js/exercises';
import {UserApi} from "../API_EJS/js/user";
import {FavApi} from "../API_EJS/js/favourites";

export const getRoutines = async ({ commit }) => {
    const response = await routineApi.getAll("", null);
    if (!response.code){
        // console.log("ENTRO");
        // console.log(response);
        commit('SET_OTHERS', response.content);
    }
}

export const searchRoutines = async ({ commit }, busqueda) => {
    const response = await routineApi.getAll("search=" + busqueda, null);
    if (!response.code){
        // console.log("ENTRO");
        // console.log(response);
        commit('SET_OTHERS', response.content);
    }
}
export const getUserRoutines = async ({ commit }) => {
    const response = await UserApi.getRoutines( '',null);
    if (!response.code){
        // console.log("ENTRO");
        // console.log(response);
        commit('SET_ROUTINES', response.content);
    }
}
export const searchUserRoutines = async ({ commit },busqueda) => {
    const response = await UserApi.getRoutines( "search=" + busqueda,null);
    if (!response.code){
        // console.log("ENTRO");
        // console.log(response);
        commit('SET_ROUTINES', response.content);
    }
}

export const getFavourites = async ({ commit }) => {
    const response = await FavApi.getFavourites(null);
    if (!response.code){
        // console.log("ENTRO");
        console.log(response);
        commit('SET_FAVOURITES', response.content);
    }
}

export const getExercises = async ({ commit }) => {
    const response = await ExerciseApi.getAll("size=20",null);
    if (!response.code){
        commit('SET_EXERCISES', response.content);
    }
}

export const searchExercises = async ({ commit }, busqueda) => {
    const response = await ExerciseApi.getAll("search=" + busqueda,null);
    if (!response.code){
        commit('SET_EXERCISES', response.content);
    }
}


export const changeCardID = ({ commit }) => {
    commit('INCREMENT_ID');
}