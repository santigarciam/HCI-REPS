<template>
  <div class="bg">
    <v-img>
      <v-container fluid class="fill-height pa-0 ma-0">
        <v-row>
          <v-col>
            <v-row justify="center" align="end" style="height: 550px">
              <v-card v-show="!buttonPressed" rounded color="transparent" elevation="0">
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
                          <v-card color="rgb(0, 0, 0, 0.7)">
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
                                            required
                                            filled
                                            background-color="white"
                                            rounded
                                            dense
                                            light
                                            class= "mt-6"
                                            :rules="usernameRules"
                                        ></v-text-field>
                                      </v-row>

                                      <!--Registro mail -->
                                      <v-row>
                                        <v-text-field
                                            label="Correo electrónico *"
                                            v-model="email"
                                            required
                                            :rules=emailRules
                                            filled
                                            rounded
                                            background-color="white"
                                            light
                                            dense
                                        ></v-text-field>
                                      </v-row>

                                      <!--Registro contrasena -->
                                      <v-row>
                                        <v-text-field
                                            v-model="newPassword"
                                            filled
                                            rounded
                                            dense
                                            background-color="white"
                                            light
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
                                            rounded
                                            background-color="white"
                                            light
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

                                      <v-row>
                                        <v-btn
                                            block
                                            elevation="2"
                                            color="#2679CC"
                                            dark
                                            rounded
                                            @click="validar()"
                                            @keyup.enter="validar()"
                                        >REGISTRARSE
                                        </v-btn>
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
                          <v-card>
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
                                          v-model="password"
                                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                          :type="show1 ? 'text' : 'password'"
                                          :rules="loginPasswordRules"
                                          @keyup.enter="validarLogIn"
                                          @click:append="show1 = !show1"
                                      ></v-text-field>
                                    </v-row>

                                    <v-row>
                                      <v-btn
                                          block
                                          elevation="2"
                                          color="#00B2EB"
                                          dark
                                          rounded
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
        <v-snackbar bottom color="error" v-model="error">
          <p> {{error}} </p>
        </v-snackbar>
      </v-container></v-img>
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
// import { Api } from "../API_EJS/js/api";
// import state from "../store/state";
import {bus2} from "../main"

export default {
  name: "LandingPage",
  data() {
    return {
      error: "",
      valid: false,
      //reglas para el form
      usernameRules: [
        v => !!v || 'El usuario es obligatorio'
        //ver que pasa si quiere usar un username ya existente
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
        v => this.check(v)|| 'La contraseña no es igual',
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

      //v-models de inicio de sesion
      username: "",
      password: "",
      token: "",

      //v-models de registrarse
      newUsername: "",
      email: "",
      newPassword: "",
      confirmPassword: "",


      buttonPressed: false,

    }
  },
  methods:{
    check: function (password){
      return password == this.newPassword
    },
    resetearCampos: function (){
      this.$refs.form.reset()
    },
    resetear: function (){
      this.$refs.form2.reset()
    },
    validar: function (){
      if (this.$refs.form.validate() == true){
        this.registerUser() //acá habria q chequear si el username o el mail ya existen porq tira error

      }//hay q hacer un else??

    },
    validarLogIn: function (){
      if (this.$refs.form2.validate() == true){
        this.loginUser() //acá habria q chequear si el username o el mail ya existen porq tira error
        bus2.$on('errorLogIn', (data) =>{
          this.error = data;
        })
      }//hay q hacer un else??

    },
    registerUser: function (){
      if(this.newUsername === ""){
        console.log("Usuario vacio") ;
        console.log(this.newUsername);
      }
      UserApi.register({username:this.newUsername,password: this.newPassword,firstName:"leonel",
        lastName:'parisian',gender:'male', birthdate:29021990,email:this.email,phone:'234532123',
        avatarUrl:'https://flic.kr/p/3ntH2u',metadata:null},null);
    },
    loginUser: function(){
      console.log(this.username);
      UserApi.login({username: this.username, password: this.password},null);
      //console.log(JSON.stringify(UserApi.constructor(this.username, this.password)));
      //
    }

  }
};

</script>