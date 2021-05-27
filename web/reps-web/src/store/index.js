import Vue from 'vue';
import VueX from "vuex";

Vue.use(VueX);

import state from "./state";
import * as getters from "./getters";
import * as mutations from "./mutations";
import * as actions from "./actions";

export default new VueX.Store({
    state,
    getters,
    mutations,
    actions
});
