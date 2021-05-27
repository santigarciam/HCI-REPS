<template>
<v-app>
    <AppBar v-if="showAppBar"></AppBar>
  <v-main>
    <router-view></router-view>
  </v-main>
</v-app>

</template>

<script>

import AppBar from "./components/AppBar";

export default {
  name: 'App',

  components: {
    AppBar,
  },
  computed: {
    isInPageToShowAppBar(){
      return this.$route.path === '/MisRutinas' || this.$route.path === '/MisEjercicios' || this.$route.path === '/Descubrir' || this.$route.path === '/Favoritos';
    },
    showAppBar(){
      return this.isInPageToShowAppBar;
    }
  },
};

</script>
