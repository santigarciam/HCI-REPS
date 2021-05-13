<template>
<v-app>
    <AppBar v-if="showAppBar" ></AppBar>
  <v-main>
    <router-view></router-view>
  </v-main>
</v-app>

</template>

<script>

import AppBar from "./components/AppBar";
//import state from "./store/state";
//import {Api} from "./API_EJS/js/api"

export default {
  name: 'App',

  components: {
    AppBar,
  },
  computed: {
    isNotHome() {
      return this.$route.path != '/'
    },
    isNotConfirm(){
      return this.$route.path != '/ConfirmacionMail'
    },
    showAppBar(){
      return this.isNotHome && this.isNotConfirm
    }
  },


};

</script>
