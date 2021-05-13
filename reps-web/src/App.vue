<template>
<v-app>
    <AppBar v-if="isNotLoged" ></AppBar>
  <v-main>
    <router-view></router-view>
  </v-main>
</v-app>

</template>

<script>

import AppBar from "./components/AppBar";
import state from "./store/state";

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
      return state.token != null;
    },
    showAppBar(){
      return this.isNotLoged() | this.$route.path !== '/ConfirmacionMail'
    }
  },


};

</script>
