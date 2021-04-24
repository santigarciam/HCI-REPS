<template>
  <nav>
    <v-toolbar>
      <router-link to="/Home">
      <img class="mr-3" :src="require('../assets/reps1.png')" height="40"/>
      </router-link>
      <router-link to="/Home">
      <v-toolbar-title><span class="titulo1 font-weight-light">RE</span><span class="titulo2">PS</span></v-toolbar-title>
      </router-link>
      <v-spacer></v-spacer>
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->
      <div class="text-center">
        <v-btn
            rounded
            color="primary"
            dark
        >
          Home
        </v-btn>
      </div>
      <!--  -------  -->
      <v-spacer></v-spacer>
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->
      <div class="text-center">
        <v-btn
            rounded
            color="primary"
            dark
        >
          Mis Rutinas
        </v-btn>
      </div>
      <!--  -------  -->
      <v-spacer></v-spacer>
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->
      <div class="text-center">
        <v-btn
            rounded
            color="primary"
            dark
        >
          Mis Ejercicios
        </v-btn>
      </div>
      <!--  -------  -->
      <v-spacer></v-spacer>
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->
      <div class="text-center">
        <v-btn
            rounded
            color="primary"
            dark
        >
          Descubrir
        </v-btn>
      </div>
      <!--  -------  -->
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>mdi-circle</v-icon>
      </v-btn>
    </v-toolbar>
    </nav>
</template>

<script>
export default {
  name: "AppBar"
}
</script>

<style scoped>

a { text-decoration: none; }

.titulo1 {
  color: dodgerblue;
  font-weight: bold;
}

.titulo2 {
  color: darkblue;
  font-weight: bold;
}

</style>