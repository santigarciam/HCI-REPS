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
                        <v-dialog v-model="dialog" dark max-width="400px">
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
                              <v-row>
                                <v-col cols="12">

                                  <!--Input registro usuario -->
                                  <v-container v-show="!verification">
                                    <v-row>
                                      <v-text-field
                                          label="Usuario *"
                                          v-model="usernameReg"
                                          required
                                          filled
                                          background-color="white"
                                          rounded
                                          dense
                                          light
                                          class= "mt-6"
                                          :rules="[rules.required, rules.minUsername]"
                                      ></v-text-field>
                                    </v-row>

                                    <!--Input registro email -->
                                    <v-row>
                                      <v-text-field
                                          label="Correo electrónico *"
                                          v-model="emailReg"
                                          required
                                          :rules="[rules.required, rules.email]"
                                          filled
                                          rounded
                                          background-color="white"
                                          light
                                          dense
                                      ></v-text-field>
                                    </v-row>

                                    <!--Input registro contrasena -->
                                    <v-row>
                                      <v-text-field
                                          v-model="passReg"
                                          filled
                                          rounded
                                          dense
                                          background-color="white"
                                          light
                                          required
                                          :append-icon="
                                          show1 ? 'mdi-eye' : 'mdi-eye-off'
                                        "
                                          :type="show1 ? 'text' : 'password'"
                                          :rules="[rules.required, rules.min]"
                                          name="input-10-1"
                                          label="Contraseña *"
                                          hint="Mínimo 8 caracteres"
                                          counter
                                          @click:append="show1 = !show1"
                                      ></v-text-field>
                                    </v-row>
                                    <v-row>
                                      <v-text-field
                                          v-model="confirmPassword"
                                          filled
                                          rounded
                                          background-color="white"
                                          light
                                          dense
                                          required
                                          :append-icon="
                                          show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                          :type="show1 ? 'text' : 'password'"
                                          :rules="[comparePassword]"
                                          name="input-10-1"
                                          label="Confirmar contraseña *"
                                          counter
                                          @keyup.enter="registerUser"
                                          @click:append="show1 = !show1"
                                      ></v-text-field>
                                    </v-row>
                                    <v-row>
                                      <v-btn
                                          block
                                          elevation="2"
                                          color="#2679CC"
                                          dark
                                          rounded
                                          @click="registerUser()"
                                          @keyup.enter="registerUser"
                                      >REGISTRARSE
                                      </v-btn>
                                    </v-row>
                                  </v-container>


                                </v-col>
                              </v-row>
                            </v-card-text>
                          </v-card>
                        </v-dialog>

                        <!--INICIO SESION-->
                        <v-dialog v-model="dialog2" dark max-width="400px">
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
                              <v-row>
                                <v-col cols="12">
                                  <!--Input registro usuario -->
                                  <v-container v-show="!verification">
                                    <v-row>
                                      <v-text-field
                                          label="Usuario"
                                          filled
                                          class= "mt-6"
                                          rounded
                                          dense
                                          v-model="username"
                                      ></v-text-field>
                                    </v-row>
                                    <v-row>
                                      <v-text-field
                                          label="Contraseña"
                                          filled
                                          rounded
                                          dense
                                          v-model="password"
                                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                          :type="show1 ? 'text' : 'password'"
                                          name="input-10-1"
                                          @keyup.enter="loginUser"
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
                                          @click="loginUser()"
                                      >INICIAR SESION
                                      </v-btn>
                                    </v-row>
                                  </v-container>
                                </v-col>
                              </v-row>
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

export default {
  name: "LandingPage",
  data() {
    return {
      show1: false,
      dialog: false,
      username: "",
      password: "",
      token: "",
      usernameReg: "",
      emailReg: "",
      passReg: "",
      confirmPassword: '',
      verification: false,
      verificationInput: "",
      buttonPressed: false,

      rules: {
        required: (value) => !!value || "Obligatorio",
        min: (v) => v.length >= 8 || "Mínimo 8 caracteres",
        minUsername: (v) => v.length >= 1 || "Ingrese un nombre de usuario",
        email: value => {
          const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
          return pattern.test(value) || 'Correo inválido.'
        }
      }
    }
  },
        methods:{
          registerUser: function (){
            if(this.usernameReg === ""){
              console.log("Usuario vacio") ;
              console.log(this.usernameReg);
            }
            UserApi.register({username:this.usernameReg,password: this.passReg,firstName:"leonel",lastName:'parisian',gender:'male',birthdate:29021990,email:this.emailReg,phone:'234532123',avatarUrl:'https://flic.kr/p/3ntH2u',metadata:null},null);
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
