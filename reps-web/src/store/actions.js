import {routineApi} from '../API_EJS/js/routines';
import {ExerciseApi} from '../API_EJS/js/exercises';
import {UserApi} from "../API_EJS/js/user";
import {FavApi} from "../API_EJS/js/favourites";
import {cycleApi} from "../API_EJS/js/cycles";
import {cycleExercisesApi} from "../API_EJS/js/cycleExercises";

// export const loadResources = async ({ commit }) => {
//     console.log("EMPIEZO loadResources");
//     //await this.getRoutines(commit, "");
//     //await this.getUserRoutines(commit, "");
//     await this.getFavourites(commit);
//     await this.getExercises(commit);
//     await this.getUserInformation(commit);
//     console.log("TERMINO loadResources");
// }

export const getRoutineByID = async ({ commit}, urlRut) => {
    // console.log("actions: LLEGO");
    // // console.log("actions: ");
    //  console.log(urlRut);
    const response = await routineApi.getByURL(urlRut, null);
    console.log(response);
    if (!response.code){
        const rutina = response;
        const result = await cycleApi.getAll(rutina.id, null);
        if (result.content) {
            rutina.ciclosRut = result.content;
            for (const ciclo of rutina.ciclosRut) {
                const resp = await cycleExercisesApi.getAll(ciclo.id, null);
                if (resp.totalCount) {
                    ciclo.ciclosEjs = resp.content;
                } else {
                    console.log("error al traer los ejs"); /// ERROR
                }
            }
        } else {
            console.log("error al traer los ciclos")
        }
        console.log(rutina)
        commit('SET_SHARED_RUT', rutina);
        // console.log(response.content)
    }
    //console.log(response);
}

export const getRoutines = async ({ commit}, parameters) => {
    const response = await routineApi.getAll(parameters, null);
    if (!response.code){
       // var aux = response.content.filter(n => n.user.id != state.userID)
        for (const rutina of response.content) {
            const result = await cycleApi.getAll(rutina.id, null);
            if (result.content) {
                rutina.ciclosRut = result.content;
                for (const ciclo of rutina.ciclosRut) {
                    const resp = await cycleExercisesApi.getAll(ciclo.id, null);
                    if (resp.totalCount) {
                        ciclo.ciclosEjs = resp.content;
                    } else {
                        console.log("error al traer los ejs"); /// ERROR
                    }
                }
            } else {
                console.log("error al traer los ciclos")
            }
        }
        commit('SET_OTHERS', response.content);
        console.log(response.content)
    }
}

export const getUserRoutines = async ({ commit }, parameters) => {
    const response = await UserApi.getRoutines(parameters, null);
    if (!response.code) {
        // console.log("ENTRO");
        // console.log(response);
        for (const rutina of response.content) {
            const result = await cycleApi.getAll(rutina.id, null);
            if (result.content) {
                rutina.ciclosRut = result.content;
                for (const ciclo of rutina.ciclosRut) {
                    const resp = await cycleExercisesApi.getAll(ciclo.id, null);
                    if (resp.totalCount) {
                        ciclo.ciclosEjs = resp.content;
                    } else {
                        console.log("error al traer los ejs"); /// ERROR
                    }
                }
            } else {
                console.log("error al traer los ciclos")
            }
        }
        commit('SET_ROUTINES', response.content);
        console.log(response.content)
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
        var aux = [];
        for (const rutina of response.content) {
            aux.push(rutina.id)
            const result = await cycleApi.getAll(rutina.id, null);
            if (result.content) {
                rutina.ciclosRut = result.content;
                for (const ciclo of rutina.ciclosRut) {
                    const resp = await cycleExercisesApi.getAll(ciclo.id, null);
                    if (resp.totalCount) {
                        ciclo.ciclosEjs = resp.content;
                    } else {
                        console.log("error al traer los ejs"); /// ERROR
                    }
                }
            } else {
                console.log("error al traer los ciclos")
            }
        }
        commit('SET_FAVOURITES', response.content);
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
    // console.log("En actions: " + id);
    commit('GET_CYCLES_OF_ID', id);
}

export const saveUserInfo = ({commit},info) =>{
    commit('SAVE_USER_INFO',info);
}


export const changeCardID = ({ commit }) => {
  // console.log("antes del increment")
    commit('INCREMENT_ID');
  // console.log("despues del increment")
}

export const getUserInformation = ({ commit }) => {
    commit('GET_USER_INFO');
}
export const setSelectedExercisesInCycles = ({ commit }, arr) => {
    commit('SET_EX_IN_CYCLE', arr);
}
