<template>
  <nav :key="cardID">
    <v-app-bar fixed app color="black">
      <router-link to="/MisRutinas">
      <img class="mr-3" :src="require('../assets/reps4.png')" height="40"/>
      </router-link>
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

        <v-menu offset-y
            transition="slide-y-transition"
            bottom
            clas="ma-0 pt-0"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon
                color="black"
                dark
                v-bind="attrs"
                v-on="on"
                   class="mr-4"
            >
              <v-avatar>
                <img :key="cardID"
                    v-bind:src="userAvatar"
                    v-bind:alt="userName + userLastName"
                >
              </v-avatar>
            </v-btn>
          </template>
          <v-list clas="mt-0 pt-0">
            <v-list-item  disabled>

              <v-btn block class="elevation-0" color="#2679CC" dark :key="cardID">
                <v-icon left>
                  mdi-account
                </v-icon>
                {{user}}
              </v-btn>
            </v-list-item>
            <v-list-item>
             <perfil-pop-up></perfil-pop-up>
            </v-list-item>
            <v-list-item>
              <v-row>
                <v-dialog v-model="dialog" width="800px">
                  <template  v-slot:activator="{ on, attrs }">
                    <v-btn block plain v-bind="attrs" v-on="on">
                    <!--         slot="activator"             -->
                      Cerrar Sesión
                    </v-btn>
                  </template>
                  <v-card>
                    <v-card-title>¿Está seguro que desea cerrar sesión? </v-card-title>
                    <v-col text--center>
                      <v-row>
                        <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                        <v-btn flat :disabled="loading"
                               class="mx-0"
                               color="#2679CC"
                               dark
                               @click="funToCancel">No</v-btn>
                        <v-btn :loading="loading" dark color="grey lighten-1 white--text mx-10" @click="logOut()">Si</v-btn>
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

<!--      -->
    </v-app-bar>
    </nav>
</template>

<script>

import PerfilPopUp from "@/components/perfilPopUp";
import state from "../store/state";
import {router} from "../main"
import { UserApi } from "../API_EJS/js/user";

export default {
  name: "AppBar",
  components: {PerfilPopUp},
  data() {
    return {
      loading: false,
    }
  },
  methods:{
    logOut: function (){
      UserApi.logout()
      this.toLanding()
    },
    funToCancel: function (){
      this.dialog = false;
      this.$store.dispatch("changeCardID");
    },
    toLanding: async function (){
      await this.loadingAnimation();
      state.token = null;
      await router.push('/');
    },
    async loadingAnimation () {
      this.loading = true;
      await new Promise(resolve => setTimeout(resolve, 2000));
      this.loading = false;
    },
    getUserInformation: async function (){
      const result = await UserApi.getCurrentUser(null);
      this.data.user = result.username;
      this.data.userFirstName = result.firstName;
      this.data.userAvatar = result.avatarUrl;
      this.data.userInfoLoaded = true;
    }
  },
  computed: {
    cardID(){
      return this.$store.state.cardID;
    },
    user(){
      return this.$store.state.user;
    },
    userName(){
      return this.$store.state.userFirstName;
    },
    userLastName(){
      return this.$store.state.userLastName;
    },
    userAvatar(){
      return this.$store.state.userAvatar;
    }
  },
  mounted() {
    this.$store.dispatch("getUserInformation");
  }

}

</script>

<style scoped>

a { text-decoration: none; }

.negro{
  color: black !important;
}

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