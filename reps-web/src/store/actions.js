import {Api} from '../API_EJS/js/api';

export const getRoutines = async ({ commit }) => {
    // console.log(Api.baseUrl + '/routines');
    const response = await Api.get(Api.baseUrl + '/routines', false, null);
    console.log(Promise.resolve(response));
    if (!response.code){
        console.log("ENTRO");
        console.log(response.content);
        commit('SET_ROUTINES', response.data);
    }

}