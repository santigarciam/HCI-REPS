import {cycleApi} from "../API_EJS/js/cycles";

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