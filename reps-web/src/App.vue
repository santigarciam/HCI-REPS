<template>
<v-app>
    <AppBar v-if="isNotLoged || isNotHome" ></AppBar>
  <v-main>
    <router-view></router-view>
  </v-main>
</v-app>

</template>

<script>

import AppBar from "./components/AppBar";
//import state from "./store/state";
import {Api} from "./API_EJS/js/api"

export default {
  name: 'App',

  components: {
    AppBar,
  },
  computed: {
    isNotHome() {
      return this.$route.path != '/'
    },
    isNotLoged(){
      return ! Api.getToken() ;
    },
    showAppBar(){
      return this.isNotLoged() | this.$route.path !== '/ConfirmacionMail'
    }
  },


};

</script>
