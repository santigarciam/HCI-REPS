import {cycleApi} from "../API_EJS/js/cycles";
import {UserApi} from "../API_EJS/js/user";

export const SET_ROUTINES = (state, rutinas) => {
    state.listaRutinas = rutinas;
}

export const SET_FAVOURITES = (state, rutinas) => {
    state.rutinasFavoritas = rutinas;
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
    console.log("En mutations: " + id);
    const result = await cycleApi.getAll(id, null);
    if (result.content){
        state.cyclesOfRutine = result.content;
    }else{
        console.log("ERROR"); //ERROR
    }
}

export const GET_USER_INFO = async (state) => {
    const result = await UserApi.getCurrentUser(null);
    console.log(result);
    state.user= result.username;
    state.userFirstName = result.firstName;
    state.userLastName = result.lastName;
    state.userAvatar = result.avatarUrl;
    state.userDate = result.birthdate;
}