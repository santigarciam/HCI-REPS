<template>
<v-app>
    <AppBar v-if="isNotHome" ></AppBar>
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
    isNotHome() {
      return this.$route.path != '/'
    }
  },


};

</script>
