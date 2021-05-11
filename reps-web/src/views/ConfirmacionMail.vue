<template>
  <v-card >
    <v-card-text>
      <v-card-title>PONER QUE SE LE ENVIO UN MAIL BLA BLA BLA. VER COMO CENTRAR LAS COSAS </v-card-title>
      <v-row>
        <v-col cols="12">

          <!--Input registro usuario -->
          <v-container>
            <v-row>
              <v-text-field
                  label="Codigo"
                  filled
                  class= "mt-6"
                  rounded
                  dense
                  v-model="verificationCode"
                  @keyup.enter="verifyCode"
              ></v-text-field>
            </v-row>

            <v-row class="text-center">

              <v-btn
                  elevation="2"
                  color="#00B2EB"
                  dark
                  rounded
                  center
                  class="text-center"
                  v-on:click="verificarCodigo"
                  @keyup.enter="verificarCodigo"
              >CONFIRMAR
              </v-btn>
            <v-btn
                elevation="2"
                color="#00B2EB"
                dark
                class="text-center"
                rounded
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
</template>

<script>
import { UserApi } from "@/API_EJS/js/user";


export default {
  name: "ConfirmacionMail",
  data(){
    return {
      verificationCode: "",
      snackbar:false,
    }
  },

    methods:{
      verificarCodigo(){
        console.log("ACAAA");
        // eslint-disable-next-line no-undef
        console.log(this.$store.state.userRegisteredMail);
        // console.log({userRegisteredMail,code:this.verificationCode});
         UserApi.verifyCode({email:this.$store.state.userRegisteredMail,code:this.verificationCode},null);
      },
      resendCode(){
        console.log("reenviado");
        UserApi.resendCode({email:this.$store.state.userRegisteredMail},null);
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

</style>