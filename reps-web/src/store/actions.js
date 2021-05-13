import {routineApi} from '../API_EJS/js/routines';
import {ExerciseApi} from '../API_EJS/js/exercises';
import {UserApi} from "../API_EJS/js/user";
import {FavApi} from "../API_EJS/js/favourites";


export const getRoutines = async ({ commit}, parameters) => {
    const response = await routineApi.getAll(parameters, null);
    if (!response.code){
       // var aux = response.content.filter(n => n.user.id != state.userID)
        commit('SET_OTHERS', response.content);
        console.log(response.content)
    }
}

export const getUserRoutines = async ({ commit }, parameters) => {
    const response = await UserApi.getRoutines( parameters,null);
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
        commit('SET_FAVOURITES', response.content);
        var aux = [];
        response.content.forEach(e => aux.push(e.id));
        console.log(aux)
        commit('SET_ID_FAVOURITES', aux);
    }
}

export const addFavourites = async ({ commit },id) => {
    const response = await FavApi.add(id,null);
    if (!response.code){
        commit('UPDATE_ID_FAVOURITES');
    }
}

export const deleteFavourites = async ( {commit}, id) => {
    const response = await FavApi.delete(id,null);
    if (!response.code){
        commit('UPDATE_ID_FAVOURITES');
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

export const saveUserInfo = ({commit},info) =>{
    commit('SAVE_USER_INFO',info);
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
