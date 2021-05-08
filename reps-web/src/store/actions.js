import {routineApi} from '../API_EJS/js/routines';
import {ExerciseApi} from '../API_EJS/js/exercises';

export const getRoutines = async ({ commit }) => {
    const response = await routineApi.getAll(null);
    if (!response.code){
        // console.log("ENTRO");
        // console.log(response);
        commit('SET_ROUTINES', response.content);
    }
}

export const getExercises = async ({ commit }) => {
    const response = await ExerciseApi.getAll("size=20",null);
    if (!response.code){
        commit('SET_EXERCISES', response.content);
    }
}

export const changeCardID = ({ commit }) => {
    commit('INCREMENT_ID');
}