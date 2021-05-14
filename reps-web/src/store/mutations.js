import {cycleApi} from "../API_EJS/js/cycles";
import {UserApi} from "../API_EJS/js/user";
import {cycleExercisesApi} from "../API_EJS/js/cycleExercises";

export const SET_ROUTINES = (state, rutinas) => {
    state.listaRutinas = rutinas;
}

export const SET_FAVOURITES = (state, rutinas) => {
    state.rutinasFavoritas = rutinas;
}

export const SET_ID_FAVOURITES = (state, ids) => {
    state.idFavoritas = ids;
}
export const UPDATE_ID_FAVOURITES = (state) => {
   console.log(state.idFavoritas)
}

export const SET_OTHERS = (state, rutinas) => {
    state.otrasRutinas = rutinas;
}

export const SET_EXERCISES = (state, ejercicios) => {
    state.listaEjercicios = ejercicios;
}

export const INCREMENT_ID = (state) => {
    state.cardID += 1;
}
export const GET_CYCLES_OF_ID = async (state, id) => {
    // console.log("En mutations: " + id);
    const result = await cycleApi.getAll(id, null);
    if (result.content){
        state.cyclesOfRutine = result.content;

        let respExercises;
        let i = 0;
        for(const ciclo of result.content){
            respExercises = await cycleExercisesApi.getAll(ciclo.id,null);
            if(respExercises.totalCount){
            state.exersisesOfRoutineOnCycle[i++] = respExercises.content;
                // console.log(" AL TRAER LOS EJS"); /// ERROR
            // console.log(state.exersisesOfRoutineOnCycle);
            }else{
                console.log("ERROR AL TRAER LOS EJS"); /// ERROR
            }
        }
        for(var j=0;j< state.exersisesOfRoutineOnCycle.length;j++){
            this.state.exersisesOfRoutineOnCycle[j].orderBy(ej => ej.id);
        }//////DESCOMENTAR CUANDO EL API FUNCIONE
    }else{
        console.log("ERROR"); //ERROR
    }
}

export const GET_USER_INFO = async (state) => {
    const result = await UserApi.getCurrentUser(null);
    console.log(result);
    state.userID = result.id;
    state.user= result.username;
    state.userFirstName = result.firstName;
    state.userLastName = result.lastName;
    state.userAvatar = (result.avatarUrl === 'https://flic.kr/p/3ntH2u')? state.userAvatar : result.avatarUrl;
    state.userDate = result.birthdate;
}

export const SAVE_REGISTERED_MAIL = (state,mail) =>{
    state.userRegisteredMail = mail;
}

export const SET_EX_IN_CYCLE = (state,arr) =>{
    state.ejsCycleAux = arr;
}

export const SAVE_USER_INFO = (state,info)=>{
    state.userInfo = info;
}
