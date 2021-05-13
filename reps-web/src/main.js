import Vue from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import VueRouter from 'vue-router';
import MisEjercicios from './views/MisEjercicios';
import MisRutinas from './views/MisRutinas';
import Descubrir from './views/Descubrir';
import NotFound from './views/NotFound';
import Favoritos from "./views/Favoritos";
import LandingPage2 from "./views/LandingPage2"
import ConfirmacionMail from "@/views/ConfirmacionMail";
import store from "./store";

//import state from "./store/state";
import {Api} from "./API_EJS/js/api";

export const bus = new Vue();
export const bus2 = new Vue();

Vue.use(VueRouter);

Vue.config.productionTip = false
Vue.config.silent = true

export const router = new VueRouter({
  routes: [
    {path:'/', component: LandingPage2},
    {path:'/MisRutinas', component: MisRutinas, meta: {requiresAuth: true}},
    {path:'/MisEjercicios', component: MisEjercicios, meta: {requiresAuth: true}},
    {path:'/Favoritos', component: Favoritos, meta: {requiresAuth: true}},
    {path:'/Descubrir', component: Descubrir, meta: {requiresAuth: true}},
    {path:'/ConfirmacionMail',component: ConfirmacionMail},
    {path:'*', component: NotFound}
  ]
})

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)){
    if (! Api.getToken()){
      next({
        path: '/'
      });
    }else{
      next();
    }
  }else{
    next();
  }
});

Api.restoreToken();

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')

