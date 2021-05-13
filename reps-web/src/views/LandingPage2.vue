<template>
  <div class="bg" :key="cardID">
    <v-img>
      <v-container fluid class="fill-height pa-0 ma-0">
        <v-row>
          <v-col>
            <v-row justify="center" align="end" style="height: 550px">
              <v-card v-show="!this.dialog && !this.dialog2 && !this.dialogRegist" rounded color="transparent" elevation="0">
                <v-card-title class="justify-center white--text">
                  <h1 class="frase">EMPIECE A CREAR RUTINAS PARA ENTRENAR</h1>
                </v-card-title>
                <v-card-text>

                  <v-row align="center" justify="center" class="mt-2">
                    <template>
                      <v-row justify="center">
                        <v-dialog @click:outside="resetearCampos()" v-model="dialog" dark max-width="400px">
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
                          <v-card color="rgb(0, 0, 0, 0.8)">
                            <v-card-text>
                              <v-form ref="form" v-model="valid">
                                <v-row>
                                  <v-col cols="12">

                                    <!--Registro usuario -->
                                    <v-container>
                                      <v-row>
                                        <v-text-field
                                            label="Usuario *"
                                            v-model="newUsername"
                                            filled
                                            class= "mt-6"
                                            rounded
                                            :error-messages= "this.usernameError"
                                            dense
                                            required
                                            :rules="usernameRules"
                                        ></v-text-field>
                                      </v-row>

                                      <!--Registro mail -->
                                      <v-row>
                                        <v-text-field
                                            label="Correo electrónico *"
                                            v-model="email"
                                            :rules=emailRules
                                            :error-messages= "this.emailError"
                                            filled
                                            class= "mt-6"
                                            rounded
                                            dense
                                            required
                                        ></v-text-field>
                                      </v-row>

                                      <!--Registro contrasena -->
                                      <v-row>
                                        <v-text-field
                                            v-model="newPassword"

                                            filled
                                            class= "mt-6"
                                            rounded
                                            dense
                                            required
                                            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                            :type="show1 ? 'text' : 'password'"
                                            :rules="passwordRules"
                                            name="input-10-1"
                                            label="Contraseña *"
                                            hint="Mínimo 8 caracteres"
                                            counter
                                            @click:append="show1 = !show1"
                                        ></v-text-field>
                                      </v-row>

                                      <!--Confirmar contrasena -->
                                      <v-row>
                                        <v-text-field
                                            v-model="confirmPassword"

                                            filled
                                            class= "mt-6"
                                            rounded
                                            dense
                                            required
                                            :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                                            :type="show2 ? 'text' : 'password'"
                                            :rules="confirmRules"
                                            name="input-10-1"
                                            label="Confirmar contraseña *"
                                            counter
                                            @click:append="show2 = !show2"
                                        ></v-text-field>
                                      </v-row>
                              <!--REGISTRARSEEEEEEEEEEEEEEEEEEEEeeee-->
                                      <v-row>
                                          <v-btn
                                              :loading="loading"
                                            block
                                            elevation="2"
                                            color="#2679CC"
                                            dark
                                            rounded
                                            @click="validar()"
                                            @keyup.enter="validar()"
                                        >REGISTRARSE
                                        </v-btn>

<!--                                          FIN REGISTRARSE-->
                                      </v-row>
                                    </v-container>
                                  </v-col>
                                </v-row>
                              </v-form>
                            </v-card-text>
                          </v-card>
                        </v-dialog>

                        <!--INICIO SESION-->
                        <v-dialog @click:outside="resetear()" v-model="dialog2" dark max-width="400px">
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
//import router from 'vue-router';
import { UserApi } from "../API_EJS/js/user";
import { Api } from "../API_EJS/js/api";
// import state from "../store/state";
import {bus2, router} from "../main"
//import {changeCardID} from "../store/actions";

export default {
  name: "LandingPage",
  data() {
    return {
      loading: false,
      emailError: "",
      usernameError: "",
      loginError: false,
      loginErrorMessage: "",
      valid: false,
      dialogRegist: false,
      verificationCode: "",
      snackbar:false,
      //reglas para el form
      usernameRules: [
        v => !!v || 'El usuario es obligatorio',
       // v => this.available(v) || 'Este usuario ya esta en uso'
      ],
      emailRules: [
        v => !!v || 'El correo electrónico es obligatorio',
        v => /.+@.+\..+/.test(v) || 'Correo electrónico inválido',
      ],
      passwordRules: [
        v => !!v || 'La contraseña es obligatoria',
        v => v.length >= 8 || "Mínimo 8 caracteres"
      ],
      confirmRules: [
        v => !!v || 'Debe confirmar su contraseña',
        // v => this.check(v)|| 'La contraseña no es igual',
      ],

      //reglas login
      loginPasswordRules:[
        v => !!v || 'La contraseña es obligatoria',
      ],

      loginUsernameRules:[
        v => !!v || 'La contraseña es obligatoria',
      ],


      show1: false,
      show2: false,
      dialog: false,
      dialog2: false,

      //v-models de inicio de sesion
      username: "",
      password: "",
      token: "",

      //v-models de registrarse
      newUsername: "",
      email: "",
      newPassword: "",
      confirmPassword: "",



    }
  },
  computed: {
    usuarios(){
      return this.$store.state.allUsernames
    },
    cardID() {
      return this.$store.state.cardID;
    },
  },
  methods:{
    verificarCodigo(){
      console.log("ACAAA");
      // eslint-disable-next-line no-undef
      console.log(this.$store.state.userRegisteredMail);
      // console.log({userRegisteredMail,code:this.verificationCode});
      UserApi.verifyCode({email:this.$store.state.userRegisteredMail,code:this.verificationCode},null);
      var aux = UserApi.verifyCode({email:this.$store.state.userRegisteredMail,code:this.verificationCode},null);
      bus2.$on('error', (data) =>{
        if (data.details[0] == "Invalid verification code"){
          //mostrar el error
        }
      })
      if (aux == "verified"){
        console.log("whyy")
        UserApi.login({username: this.newUsername, password: this.newPassword},null);
      }
    },
    resendCode(){
      console.log("reenviado");
      UserApi.resendCode({email:this.$store.state.userRegisteredMail},null);
      this.snackbar = true;
      setTimeout(() => {
        this.$emit("yourEvent");
      }, this.timeout);
    },

    check: function (password){
      return password == this.newPassword
    },
   /* available: function (username){
      return ! this.usuarios.includes(username)
    },*/
    resetearCampos: function (){
      this.$refs.form.reset()
      this.emailError = ""
      this.usernameError = ""
    },
    resetear: function (){
      this.$refs.form2.reset()
      this.loginError = false
    },
    resetErrors: function (){
      this.loginError = ""
      this.emailError = ""
      this.usernameError = ""
    },
    checkError: function (){
      if (this.loginError == true){
        this.loginError = false
      }
    },
    validar: function (){
      this.loading = true;
      if (this.$refs.form.validate() == true){
        this.resetErrors()
        this.registerUser()
        bus2.$on('error', (data) =>{
          this.dialogRegist = false;
          this.dialog = true;
          if (data.code == 1){
            this.emailError = "El correo electrónico ingresado no es válido"
          }
          if (data.details[0] == "UNIQUE constraint failed: User.email"){
            this.emailError = "El correo electrónico ingresado ya se encuentra registrado"
          }
          if (data.details[0] == "UNIQUE constraint failed: User.username"){
            this.usernameError = "El usuario elegido no está disponible"
          }
        })
      }//hay q hacer un else??
    },
    validarLogIn: function (){
      this.loading=true;
      if (this.$refs.form2.validate() == true){
        this.loginUser();
        bus2.$on('error', (data) =>{
          if (data.code == 4){
            this.loginErrorMessage = "Usuario o contraseña incorrecta"
            this.loginError = true
          }
          if (data.description == "Email verification error"){
            this.loginErrorMessage = "Su correo electrónico no ha sido verificado"
            this.loginError = true
          }
        });
      }//hay q hacer un else??
    },
    registerUser: async function () {
      console.log("ACAAA");
      // this.dialogRegist =true;
      // this.dialog = false;
      if (this.newUsername === "") {
        console.log("Usuario vacio");
        console.log(this.newUsername);
      }
      const resp = await UserApi.register({
        username: this.newUsername, password: this.newPassword, firstName: "leonel",
        lastName: 'parisian', gender: 'male', birthdate: 29021990, email: this.email, phone: '234532123',
        avatarUrl: 'https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png', metadata: null
      }, null);
      if (resp.id){
            console.log("Se registro:");
            console.log(resp);
        await this.$store.dispatch('saveUserInfo', {username: this.username, password: this.password,email: this.email});
        this.loading = false;
        await router.push('/ConfirmacionMail');
      }
    },
    loginUser: async function(){
      console.log("LLEGO A LOGIN USER");
      //this.loading = true;
      console.log(this.loading);
      console.log(this.username);
      await UserApi.login({username: this.username, password: this.password}, null);
      if (Api.token){
        this.loading = false;
        await router.push('/MisRutinas');
      }
      console.log(this.loading);
      console.log("SALGO A LOGIN USER");
      this.dialogRegist = true;
      //console.log(JSON.stringify(UserApi.constructor(this.username, this.password)));
      //
    }

  }
  /*mounted() {
    this.$store.dispatch("getAllUsernames");
  }*/
}

</script>