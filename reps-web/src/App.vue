<template>
<v-app>
    <AppBar></AppBar>
    <rutineCard></rutineCard>
</v-app>

</template>

<script>

import AppBar from "./components/AppBar";
import RutineCard from "./components/rutineCard";

export default {
  name: 'App',

  components: {
    RutineCard,
    AppBar,
  },

  data: () => ({
    //
  }),
};
</script>
