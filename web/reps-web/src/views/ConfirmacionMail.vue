<template>
  <div class="bg">
    <v-container fluid class="fill-height pa-0 ma-0">
      <v-row>
        <v-col>
          <v-row justify="center" align="center" style="height: 550px">
            <v-card rounded color="rgb(0, 0, 0, 0.8)" elevation="0">
              <v-card-title class="ml-4 white--text">Ingrese el código de verificación enviado a su casilla de correos
                <v-btn class="ml-4" plain icon dark v-on:click="$router.push('/')">
                  <v-icon >
                    mdi-close
                  </v-icon></v-btn>
              </v-card-title>
              <v-card-text>
                <v-container>
                    <v-row>
                      <v-text-field
                          label="Código"
                          elevation="2"
                          color="grey"
                          dense
                          required
                          filled
                          dark
                          x-large
                          rounded
                          class="mr-3"
                          v-model="verificationCode"
                          @keyup.enter="verificarCodigo"
                      ></v-text-field>
                    </v-row>
                  <p v-if="this.verifError" class="mb-5 red--text">{{this.verifErrorMessage}}</p>
                    <v-row class="text-center" justify="center">

                      <v-btn
                          elevation="2"

                          color="#00B2EB"
                          dark
                          x-large
                          rounded
                          class="mr-3"
                          v-on:click="verificarCodigo"
                          @keyup.enter="verificarCodigo"
                      >CONFIRMAR
                      </v-btn>
                    <v-btn
                        elevation="2"
                        color="#2679CC"
                        dark
                        x-large
                        rounded
                        class="mr-3"
                        @click="resendCode"
                        @keyup.enter="resendCode"
                    >REENVIAR CÓDIGO
                    </v-btn>
              </v-row>
                  </v-container>


    </v-card-text>
    </v-card>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <v-snackbar
      v-model="snackbar"
    >Se reenvio el código de verificación a su mail {{this.mail}}</v-snackbar>
  </div>
</template>

<script>
import { UserApi } from "@/API_EJS/js/user";
//import state from "../store/state";
//import {Api} from "../API_EJS/js/api";
import {bus2, router} from "../main";
//import {router} from "../main";


export default {
  name: "ConfirmacionMail",
  data(){
    return {
      verificationCode: "",
      snackbar:false,
      verifError: false,
      verifErrorMessage: "",
      email: "",
      username: "",
      password: ""
    }
  },

  beforeMount() {
    this.getCredentials()
  },
  beforeDestroy() {
    this.clearStorage()
  },
  methods:{
      getCredentials: function (){
        this.email = localStorage.getItem('email')
        console.log(this.email)
        this.username = localStorage.getItem('username')
        console.log(this.username)
        this.password = localStorage.getItem('password')
      },
      clearStorage: function (){
        localStorage.removeItem('email')
        localStorage.removeItem('password')
        localStorage.removeItem('username')
      },
      async verificarCodigo() {
        console.log("ACAAA");
        const resp = await UserApi.verifyCode({
          email: this.email,
          code: this.verificationCode
        }, null);
        console.log(resp);
        if (resp){
          //this.$store.state.token = Api.token;
        await UserApi.login({username: this.username, password:this.password},null);
        this.clearStorage()
        await router.push('/MisRutinas');
        }
        else{bus2.$on('error', (data) => {
          this.loading = false
          if (data.details[0] == "Invalid verification code") {
            console.log("entroooooo")
            this.verifErrorMessage = "El código de verificación ingresado no es válido. Intente nuevamente"
            this.verifError = true
          }
        })}
      },
      resendCode(){
        console.log("reenviado" + this.email);
        UserApi.resendCode({email:this.email},null);
        this.snackbar = true;
        setTimeout(() => {
          this.$emit("yourEvent");
        }, this.timeout);
      }
    },
  computed:{
    getMail(){
      return this.$store.state.userRegisteredMail;
    },
  }
}
</script>

<style scoped>
.bg {
  background-image: url("../assets/principal2.png");
  height: 100%;
  background-position: top;
  background-repeat: no-repeat;
  background-size: cover;
}
</style>