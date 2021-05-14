<template>
  <v-card color="rgb(0, 0, 0, 0.7)">
    <v-card-text>
      <v-form ref="form2" v-model="valid">
        <v-row>
          <v-col cols="12">
            <!--Login usuario -->
            <v-container>
              <v-row>
                <v-text-field
                    label="Usuario"
                    filled
                    class= "mt-6"
                    rounded
                    dense
                    @keydown="checkError()"
                    required
                    :rules="loginUsernameRules"
                    v-model="username"
                    @keyup.enter="validarLogIn"
                ></v-text-field>
              </v-row>
              <!--Login contraseña -->
              <v-row>
                <v-text-field
                    label="Contraseña"
                    filled
                    rounded
                    required
                    dense
                    @keydown="checkError()"
                    v-model="password"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show1 ? 'text' : 'password'"
                    :rules="loginPasswordRules"
                    @keyup.enter="validarLogIn"
                    @click:append="show1 = !show1"
                ></v-text-field>
              </v-row>
              <p v-if="this.loginError" class="mb-5 red--text">{{this.loginErrorMessage}}</p>
              <v-row>
                <v-btn
                    block
                    elevation="2"
                    color="#00B2EB"
                    dark
                    rounded
                    :loading="loading"
                    @click="validarLogIn()"
                >INICIAR SESION
                </v-btn>
              </v-row>

            </v-container>
          </v-col>
        </v-row>
      </v-form>
    </v-card-text>
  </v-card>
</template>

<script>
import {bus2, router} from "../../main";
import {UserApi} from "../../API_EJS/js/user";
import {Api} from "../../API_EJS/js/api";

export default {
  data() {
    return {
      show1: false,
      //v-models de inicio de sesion
      username: "",
      password: "",
      token: "",

      loginError: false,
      loginErrorMessage: "",


      //reglas login
      loginPasswordRules: [
        v => !!v || 'La contraseña es obligatoria',
      ],

      loginUsernameRules: [
        v => !!v || 'La contraseña es obligatoria',
      ],

    }
  },
  methods: {
    resetearCampos: function () {
      this.$refs.form2.reset()
      this.loginError = false
    },
    resetErrors: function () {
      this.loginError = ""
    },
    checkError: function () {
      if (this.loginError == true) {
        this.loginError = false
      }
    },
    validarLogIn: function () {
      this.loading = true;
      if (this.$refs.form2.validate() == true) {
        this.loginUser();
        bus2.$on('error', (data) => {
          this.loading = false
          if (data.code == 4) {
            this.loginErrorMessage = "Usuario o contraseña incorrecta"
            this.loginError = true
          }
          if (data.description == "Email verification error") {
            this.loginErrorMessage = "Su correo electrónico no ha sido verificado"
            this.loginError = true
          }
        });
      }//hay q hacer un else??
    },

    loginUser: async function () {
      console.log("LLEGO A LOGIN USER");
      //this.loading = true;
      console.log(this.loading);
      console.log(this.username);
      await UserApi.login({username: this.username, password: this.password}, null);
      if (Api.getToken()) {
        this.loading = false;
        //await this.$store.dispatch('loadResources');
        await router.push('/MisRutinas');
      }
      console.log(this.loading);
      console.log("SALGO A LOGIN USER");
      this.dialogRegist = true;
      //console.log(JSON.stringify(UserApi.constructor(this.username, this.password)));
      //
    }
  }

}
</script>