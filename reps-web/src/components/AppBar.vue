<template>
  <nav>
    <v-toolbar color="black">
      <router-link to="/MisRutinas">
      <img class="mr-3" :src="require('../assets/reps3.png')" height="40"/>
      </router-link>
<!--      <router-link to="/Home">-->
<!--      <v-toolbar-title><span class="titulo1 font-weight-light">RE</span><span class="titulo2">PS</span></v-toolbar-title>-->
<!--      </router-link>-->
<!--      <v-spacer></v-spacer>-->
<!--      <v-spacer></v-spacer>-->
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->
      <router-link to="/MisRutinas">
      <div class="text-center pr-2">
        <v-btn
            rounded
            color="black"
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
            color="black"
            dark
        >
          Mis Ejercicios
        </v-btn>
      </div>
      </router-link>
      <!--  -------  -->
      <router-link to="/Favoritos">
        <div class="text-center pr-2">
          <v-btn
              rounded
              color="primary"
              dark
          >
            Favoritos
          </v-btn>
        </div>
      </router-link>
<!--      <v-spacer></v-spacer>-->
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->
      <router-link to="/Descubrir">
      <div class="text-center pr-2">
        <v-btn
            rounded
            color="black"
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
                color="black"
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


<!--<template>-->
<!--  <v-container fluid class="pa-0 ma-0">-->
<!--    <v-app-bar color="#202026" dark fixed app>-->
<!--      <img-->
<!--          :src="require('../assets/logoreps.png')"-->
<!--          height="100%"-->
<!--          alt=""-->
<!--      />-->

<!--      <v-col>-->
<!--        <v-tabs-->
<!--            align-with-title-->
<!--            background-color="#202026"-->
<!--            grow-->
<!--            slider-color="light blue"-->
<!--        >-->
<!--          <v-tab to="/MisRutinas">Mis Rutinas</v-tab>-->
<!--          <v-tab to="/MisEjercicios">Mis Ejercicios</v-tab>-->
<!--          <v-tab to="/Favoritos">Favoritos</v-tab>-->
<!--          <v-tab to="/Descubrir">Descubrir</v-tab>-->
<!--        </v-tabs>-->
<!--      </v-col>-->

<!--      &lt;!&ndash;menu del costadito&ndash;&gt;-->
<!--      <v-menu offset-y>-->
<!--        <template v-slot:activator="{ on, attrs }">-->
<!--          <v-btn icon v-bind="attrs" v-on="on">-->
<!--            <v-icon>mdi-account</v-icon>-->
<!--          </v-btn>-->
<!--        </template>-->
<!--        <v-list>-->
<!--          <v-list-item >-->
<!--            <v-list-item-title><h3>nombre de usuario</h3></v-list-item-title>-->
<!--          </v-list-item>-->
<!--          <v-list-item>-->
<!--            <v-list-item-title>Editar perfil</v-list-item-title>-->
<!--          </v-list-item>-->

<!--          <v-list-item-->
<!--              to="/"-->
<!--              @click="logOut()">-->
<!--            <v-list-item-title>Cerrar sesión</v-list-item-title>-->
<!--          </v-list-item>-->
<!--        </v-list>-->
<!--      </v-menu>-->
<!--    </v-app-bar>-->
<!--  </v-container>-->
<!--</template>-->

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

[aria-current="true"] {

  background-color: blue;
}

</style>