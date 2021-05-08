<template>
  <nav>
    <v-toolbar color="black">
      <router-link to="/MisRutinas">
      <img class="mr-3" :src="require('../assets/reps4.png')" height="40"/>
      </router-link>
<!--      <router-link to="/Home">-->
<!--      <v-toolbar-title><span class="titulo1 font-weight-light">RE</span><span class="titulo2">PS</span></v-toolbar-title>-->
<!--      </router-link>-->
<!--      <v-spacer></v-spacer>-->
      <v-spacer></v-spacer>

      <router-link to="/MisRutinas">
      <div class="text-center pr-2">
        <v-btn
            to="/MisRutinas"
            rounded
            :color="active ? 'primary' : 'black'"
            dark
            @click="toggle"
            active-class="activo"
        >
          Mis Rutinas
        </v-btn>
      </div>
      </router-link>
      <!--  -------  -->
      <v-spacer></v-spacer>
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->

      <router-link to="/MisEjercicios">
      <div class="text-center pr-2">
        <v-btn
            rounded
            to="/MisEjercicios"
            :color="active? 'primar':'black'"
            @click="toggle"
            dark
            active-class="activo"
        >
          Mis Ejercicios
        </v-btn>
      </div>
      </router-link>
      <v-spacer></v-spacer>
      <!--  -------  -->
      <router-link to="/Favoritos">
        <div class="text-center pr-2">
          <v-btn
              rounded
              to="/Favoritos"
              color="black"
              dark
              active-class="activo"
          >
            Favoritos
          </v-btn>
        </div>
      </router-link>
      <v-spacer></v-spacer>
      <!--   VER SI SE PUEDE HACER UN TEMPLATE   -->
      <router-link to="/Descubrir">
      <div class="text-center pr-2">
        <v-btn
            to="/Descubrir"
            rounded
            color="black"
            dark
            active-class="activo"
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
             <perfil-pop-up></perfil-pop-up>
            </v-list-item>
          </v-list>
          <v-list>
            <v-list-item>
              <v-row>
              <v-btn plain @click="toLanding">Cerrar Sesion</v-btn>
              </v-row>
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
<!--      -->
    </v-toolbar>
    </nav>
</template>

<script>

import PerfilPopUp from "@/components/perfilPopUp";
import state from "../store/state";
import {router} from "../main"

export default {
  name: "AppBar",
  components: {PerfilPopUp},
  methods:{
    toLanding: function (){
      state.token = null;
      router.push('/');
    }
  }

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
.activo {
  background-color: #00B2EB !important;
}


</style>