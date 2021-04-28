<template>
  <nav>
    <v-toolbar color="black">
      <router-link to="/Home">
      <img class="mr-3" :src="require('../assets/reps3.png')" height="40"/>
      </router-link>
<!--      <router-link to="/Home">-->
<!--      <v-toolbar-title><span class="titulo1 font-weight-light">RE</span><span class="titulo2">PS</span></v-toolbar-title>-->
<!--      </router-link>-->
<!--      <v-spacer></v-spacer>-->
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->

      <router-link to="/Home">
      <div class="text-center pr-2 pl-12">
        <v-btn
            rounded
            color="primary"
            dark
            class="style"
        >Home</v-btn>
      </div>
      </router-link>
      <!--  -------  -->
<!--      <v-spacer></v-spacer>-->
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->
      <router-link to="/MisRutinas">
      <div class="text-center pr-2">
        <v-btn
            rounded
            color="primary"
            dark
        >
          Mis Rutinas
        </v-btn>
      </div>
      </router-link>
      <!--  -------  -->
<!--      <v-spacer></v-spacer>-->
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->
      <router-link to="/MisEjercicios">
      <div class="text-center pr-2">
        <v-btn
            rounded
            color="primary"
            dark
        >
          Mis Ejercicios
        </v-btn>
      </div>
      </router-link>
      <!--  -------  -->
<!--      <v-spacer></v-spacer>-->
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->
      <router-link to="/Descubrir">
      <div class="text-center pr-2">
        <v-btn
            rounded
            color="primary"
            dark
        >
          Descubrir
        </v-btn>
      </div>
        </router-link>
      <!--  -------  -->
      <v-spacer></v-spacer>
<!--   MENU DE PERFIL   -->
      <div class="text-center">
        <v-menu offset-y
            transition="slide-y-transition"
            bottom
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon
                color="primary"
                dark
                v-bind="attrs"
                v-on="on"
            >
              <v-avatar>
                <img
                    src="https://cdn.vuetifyjs.com/images/john.jpg"
                    alt="John"
                >
              </v-avatar>
            </v-btn>
          </template>
          <v-list>
            <v-list-item>
              <v-btn plain>Mi Perfil</v-btn>
            </v-list-item>
          </v-list>
          <v-list>
            <v-list-item>
              <v-btn plain>Cerrar Sesion</v-btn>
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
<!--      -->
    </v-toolbar>
    </nav>
</template>

<script>
export default {
  name: "AppBar",
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