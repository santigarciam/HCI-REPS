<template>
<v-app>
    <AppBar></AppBar>
</v-app>

</template>

<script>

import AppBar from "./components/AppBar";

export default {
  name: 'App',

  components: {
    AppBar,
  },

  data: () => ({
    //
  }),
};
</script>
