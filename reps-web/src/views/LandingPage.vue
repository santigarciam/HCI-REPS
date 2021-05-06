<template>
  <div class="bg">
    <v-img>
      <v-container fluid class="fill-height pa-0 ma-0">
        <v-row>
          <v-col>
            <v-row justify="center" align="center" style="height: 550px">
              <v-card rounded color="transparent" elevation="0">
                <v-card-title class="justify-center white--text">
                  <h1>EMPIECE A CREAR RUTINAS PARA ENTRENAR</h1>
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
                            >REGISTRARSE</v-btn
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
                                          label="Usuario *"
                                          v-model="usernameReg"
                                          required
                                          filled
                                          rounded
                                          dense
                                          class= "mt-6"
                                          :rules="[rules.required, rules.minUsername]"
                                      ></v-text-field>
                                    </v-row>

                                    <!--Input registro email -->
                                    <v-row>
                                      <v-text-field
                                          label="Email *"
                                          v-model="emailReg"
                                          required
                                          :rules="[rules.required, rules.email]"
                                          filled
                                          rounded
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
                                          dense
                                          required
                                          :append-icon="
                                          show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                          :type="show1 ? 'text' : 'password'"
                                          :rules="[comparePassword]"
                                          name="input-10-1"
                                          label="Confirmar Contraseña *"
                                          counter
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
                                          @click="register()"
                                          @keyup.enter="register()"
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
                                          @keyup.enter="loginUser()"
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
.caja {
  font-weight: bold;
  text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.7);
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

export default {
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
          loginUser: function(){
            UserApi.login({username: this.username, password: this.password},null);
            //console.log(JSON.stringify(UserApi.constructor(this.username, this.password)));
          //
          }
        }
};

</script>
