<template>
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
                    :error-messages="this.confirmError"
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

export default {
  data (){
    return{
    show1: false,
    show2: false,
    emailError: "",
    usernameError: "",
    confirmError:"",
    loading: false,

    //v-models de registrarse
      newUsername: "",
      email: "",
      newPassword: "",
      confirmPassword: "",

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
  }
  },
  methods:{
    check: function (password){
      return password == this.newPassword
    },
    close: function(){
      this.$store.dispatch("changeCardID")
    },
    resetearCampos: function (){
      this.$refs.form.reset()
      this.resetErrors()
    },
    resetErrors: function (){
      this.emailError = ""
      this.usernameError = ""
      this.confirmError= ""
    },
    validar: function (){
      if (this.confirmPassword!="" &&!this.check(this.confirmPassword)){
        this.confirmError= 'Las contraseñas no coinciden. Vuelve a intentarlo'
        this.loading = false;
        this.dialog = true;
      }
      if (this.$refs.form.validate() == true) {
          this.loading = true
          this.resetErrors()
          this.registerUser()
          bus2.$on('error', (data) => {
            this.loading = false;
            this.dialog = true;
            if (data.code == 1) {
              this.emailError = "El correo electrónico ingresado no es válido"
            }
            if (data.details[0] == "UNIQUE constraint failed: User.email") {
              this.emailError = "El correo electrónico ingresado ya se encuentra registrado"
            }
            if (data.details[0] == "UNIQUE constraint failed: User.username") {
              this.usernameError = "El usuario elegido no está disponible"
            }
          })
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
        username: this.newUsername, password: this.newPassword, firstName: "Nombre",
        lastName: 'Apellido', gender: 'male', birthdate: 29021990, email: this.email, phone: '234532123',
        avatarUrl: 'https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png', metadata: null
      }, null);
      if (resp.id){
        console.log("Se registro:");
        console.log(resp);
        //await this.$store.dispatch('saveUserInfo', {username: this.newUsername, password: this.newPassword,email: this.email});
        localStorage.setItem('username', this.newUsername)
        localStorage.setItem('password', this.newPassword)
        localStorage.setItem('email', this.email)
        this.loading = false;
        await router.push('/ConfirmacionMail');
      }
    }
  }
}
</script>

