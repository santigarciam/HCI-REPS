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
                  @click="verifyCode"
                  @keyup.enter="verifyCode"
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
          </v-container>


        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>
import { UserApi } from "@/API_EJS/js/user";
import { LandingPage } from "@/views/LandingPage";

export default {
  name: "ConfirmacionMail",
  data(){
    return {
      verificationCode: ""
    }
  },
  methods:{
    verifyCode: function (){
      console.log(this.LandingPage.emailReg);
      console.log({email:LandingPage.emailReg,code:this.verificationCode});
      UserApi.verifyCode({email:LandingPage.emailReg,code:this.verificationCode},null);
    },
    resendCode: function (){
        UserApi.resendCode({email:LandingPage.emailReg},null);
    }
  }
}
</script>








<style scoped>

</style>