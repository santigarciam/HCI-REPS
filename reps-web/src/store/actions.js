import {routineApi} from '../API_EJS/js/routines';
import {ExerciseApi} from '../API_EJS/js/exercises';
import {UserApi} from "../API_EJS/js/user";
import {FavApi} from "../API_EJS/js/favourites";

//si le agrego lo del userID le tengo q pasar state ademas del commit pero ahora como lo comente lo saco
export const getRoutines = async ({ commit}, parameters) => {
    const response = await routineApi.getAll(parameters, null);
    if (!response.code){
       // var aux = response.content.filter(n => n.user.id != state.userID)
        commit('SET_OTHERS', response.content);
        console.log(response.content)
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

export const sortRoutines = async ({ commit }, orden) => {
    const response = await routineApi.getAll("orderBy=" + orden, null);
    if (!response.code){
        // console.log("ENTRO");
        //console.log(response);
        commit('SET_OTHERS', response.content);
    }
}


export const getUserRoutines = async ({ commit }) => {
    const response = await UserApi.getRoutines( '',null);
    if (!response.code){
        // console.log("ENTRO");
        // console.log(response);
        commit('SET_ROUTINES', response.content);
        return response.content
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
export const getCyclesOfID = ({ commit }, id) => {
    console.log("En actions: " + id);
    commit('GET_CYCLES_OF_ID', id);
}

export const saveRegisteredMail = ({commit},mail) =>{
    commit('SAVE_REGISTERED_MAIL',mail);
}


export const changeCardID = ({ commit }) => {
    commit('INCREMENT_ID');
}

export const getUserInformation = ({ commit }) => {
    commit('GET_USER_INFO');
}

export const setSelectedExercisesInCycles = ({ commit }, arr) => {
    commit('SET_EX_IN_CYCLE', arr);
}

/*
export const getAllUsernames = async ({ commit}) => {
    const response = await UserApi.getAllUsers( null);
    if (!response.code){
        var aux = [];
        response.content.forEach(e => aux.push(e.username));
        commit('SET_USERS', aux);

    }
}*/