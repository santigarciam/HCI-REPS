<template>
  <nav :key="cardID">
    <v-app-bar fixed app color="black">
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
                <img :key="cardID"
                    v-bind:src="userAvatar"
                    v-bind:alt="userName"
                >
              </v-avatar>
            </v-btn>
          </template>
          <v-list>
            <v-list-item>

              <v-btn plain disabled :key="cardID">
                {{userName + " " + userLastName}}
              </v-btn>
            </v-list-item>
            <v-list-item>
             <perfil-pop-up></perfil-pop-up>
            </v-list-item>

            <v-list-item>
              <v-row>
<!--              <v-btn plain>Cerrar Sesion</v-btn>-->
<!--                -->

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
                               color="primary"
                               @click="funToCancel">No</v-btn>
                        <v-btn plain :loading="loading" color="grey" class="mx-10" @click="toLanding">Si</v-btn>
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
      // user: '',
      // userAvatar: '',
      // userFirstName:'',
      // userInfoLoaded: false,
    }
  },
  methods:{
    funToCancel: function (){
      this.dialog = false;
      this.$store.dispatch("changeCardID");
    },
    // notLogOut: function (){
    //   this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    // },
    toLanding: async function (){
      state.token = null;
      await this.loadingAnimation();
      await router.push('/');
    },
    async loadingAnimation () {
      this.loading = true;
      console.log("Antes del promise");
      await new Promise(resolve => setTimeout(resolve, 2000));
      console.log("Despues del promise");
      this.loading = false;
    },
    getUserInformation: async function (){
      const result = await UserApi.getCurrentUser(null);
      this.data.user = result.username;
      this.data.userFirstName = result.firstName;
      this.data.userAvatar = result.avatarUrl;
      this.data.userInfoLoaded = true;
      console.log(result);
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