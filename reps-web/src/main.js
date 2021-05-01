import Vue from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import VueRouter from 'vue-router';
import MisEjercicios from './views/MisEjercicios';
import MisRutinas from './views/MisRutinas';
import Descubrir from './views/Descubrir';
import NotFound from './views/NotFound';
import Favoritos from "./views/Favoritos";
import LandingPage from "./views/LandingPage"


Vue.use(VueRouter);

Vue.config.productionTip = false

const router = new VueRouter({
  routes: [
    {path:'/', component: LandingPage},
    {path:'/MisRutinas', component: MisRutinas},
    {path:'/MisEjercicios', component: MisEjercicios},
    {path:'/Favoritos', component: Favoritos},
    {path:'/Descubrir', component: Descubrir},
    {path:'*', component: NotFound}
  ]
})

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')

