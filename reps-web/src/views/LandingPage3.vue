<template>
  <div class="bg" :key="cardID">
    <v-img>
      <v-container fluid class="fill-height pa-0 ma-0">
        <v-row>
          <v-col>
            <v-row justify="center" align="end" style="height: 550px">
              <v-card v-show="!this.dialog && !this.dialog2" rounded color="transparent" elevation="0">
                <v-card-title class="justify-center white--text">
                  <h1 class="frase">EMPIECE A CREAR RUTINAS PARA ENTRENAR</h1>
                </v-card-title>
                <v-card-text>

                  <v-row align="center" justify="center" class="mt-2">
                    <template>
                      <v-row justify="center">
                        <v-dialog @click:outside="$refs.register.resetearCampos()" overlay-opacity="0" v-model="dialog" dark max-width="400px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn
                                elevation="2"
                                color="#2679CC"
                                dark
                                x-large
                                rounded
                                class="mr-3"
                                v-bind="attrs"
                                v-on="on"

                            >REGISTRARSE</v-btn>

                          </template>
                          <register ref="register" ></register>
                        </v-dialog>

                        <!--INICIO SESION-->
                        <v-dialog @click:outside="$refs.login.resetearCampos()" v-model="dialog2" overlay-opacity="0" dark max-width="400px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn
                                elevation="2"
                                color="#00B2EB"
                                dark
                                x-large
                                rounded
                                class="mr-3"
                                v-bind="attrs"
                                v-on="on"
                            >INICIAR SESIÓN</v-btn
                            >
                          </template>
                         <login ref="login"></login>
                        </v-dialog>

                      </v-row>
                    </template>
                  </v-row>
                </v-card-text>
              </v-card>
            </v-row>
          </v-col>
        </v-row>
      </v-container>
      <v-col></v-col>
      <v-col></v-col>
    </v-img>
  </div>

</template>

<style>
.frase {
  font-weight: bold;
  text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.8);
}
.bg {
  background-image: url("../assets/principal2.png");
  height: 100%;
  background-position: top;
  background-repeat: no-repeat;
  background-size: cover;
}
</style>

<script>

import Register from "../components/Principal/Register";
import Login from "../components/Principal/Login";

export default {
  name: "LandingPage",
  components: {Login, Register},
  data() {
    return {
      loading: false,
      valid: false,
      dialog: false,
      dialog2: false,

    }
  },
  computed: {
    cardID() {
      return this.$store.state.cardID;
    },
  }
}

</script>