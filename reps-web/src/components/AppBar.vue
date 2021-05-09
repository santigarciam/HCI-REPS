<template>
  <nav :key="cardID">
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
<!--              <v-btn plain>Cerrar Sesion</v-btn>-->
<!--                -->

                <v-dialog v-model="dialog" width="800px">
                  <template  v-slot:activator="{ on, attrs }">
                    <v-btn plain v-bind="attrs" v-on="on">
                    <!--         slot="activator"             -->
                      Cerrar Sesión
                    </v-btn>
                  </template>
                  <v-card>
                    <v-card-title>¿Estás seguro de que deseas cerrar sesión? </v-card-title>
                    <v-col text--center>
                      <v-row>
                        <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                        <v-btn dark flat class="red mx-0" @click="funToCancel">No</v-btn>
                        <v-btn flat class="success mx-10" @click="toLanding">Si</v-btn>
                      </v-row>
                    </v-col>

                    <v-col></v-col>
                  </v-card>
                </v-dialog>
<!--                -->
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
    funToCancel: function (){
      this.dialog = false;
      this.$store.dispatch("changeCardID");
    },
    // notLogOut: function (){
    //   this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    // },
    toLanding: function (){
      state.token = null;
      router.push('/');
    }
  },
  computed: {
    cardID(){
      return this.$store.state.cardID;
    }
  },

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