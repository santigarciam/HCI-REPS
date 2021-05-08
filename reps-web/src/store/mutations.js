export const SET_ROUTINES = (state, rutinas) => {
    state.listaRutinas = rutinas;
}

export const SET_EXERCISES = (state, ejercicios) => {
    state.listaEjercicios = ejercicios;
}

export const INCREMENT_ID = (state) => {
    state.cardID += 1;
}