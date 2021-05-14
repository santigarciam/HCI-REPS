<template>
  <div class="bg">
    <v-container fluid class="fill-height pa-0 ma-0">
      <v-row>
        <v-col>
          <v-row justify="center" align="end" style="height: 550px">
            <v-card   rounded color="rgb(0, 0, 0, 0.8)" elevation="0">
            <v-card-text>
              <v-card-title class="white--text">Ingrese el codigo de verificacion enviado a su casilla de correos </v-card-title>
              <v-row>
                <v-col cols="12">

                  <!--Input registro usuario -->
                  <v-container>
                    <v-row>
                      <v-text-field
                          label="Codigo"
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
                          @keyup.enter="verifyCode"
                      ></v-text-field>
                    </v-row>

                    <v-row class="text-center" justify="center">

                      <v-btn
                          elevation="2"
                          color="#2679CC"
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
                        color="#00B2EB"
                        dark
                        x-large
                        rounded
                        class="mr-3"
                        @click="resendCode"
                        @keyup.enter="resendCode"
                    >REENVIAR CODIGO
                    </v-btn>

              </v-row>
                    <v-snackbar
                        v-model="snackbar"

                    >Se reenvio el codigo de verificacion a su mail {{this.$store.state.userRegisteredMail}}</v-snackbar>
                  </v-container>


                </v-col>
              </v-row>
    </v-card-text>
  </v-card>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { UserApi } from "@/API_EJS/js/user";
//import state from "../store/state";
import {Api} from "../API_EJS/js/api";
import {router} from "../main";
//import {router} from "../main";


export default {
  name: "ConfirmacionMail",
  data(){
    return {
      verificationCode: "",
      snackbar:false,
    }
  },

    methods:{
      async verificarCodigo() {
        console.log("ACAAA");
        // eslint-disable-next-line no-undef
        console.log(this.$store.state.userRegisteredMail);
        // console.log({userRegisteredMail,code:this.verificationCode});
        const resp = await UserApi.verifyCode({
          email: this.$store.state.userInfo.email,
          code: this.verificationCode
        }, null);
        console.log(resp);
        if (resp){
          this.$store.state.token = Api.token;
        await UserApi.login({username: this.$store.state.userInfo.username, password:this.$store.state.userInfo.password},null);
          await router.push('/MisRutinas');

        }
            },
      resendCode(){
        console.log("reenviado" + this.$store.state.userRegisteredMail);
        UserApi.resendCode({email:this.$store.state.userInfo.email},null);
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