<template>
  <v-dialog v-model="dialog" width="800px">
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
      <v-btn plain block slot="activator" v-bind="attrs" v-on="on">Mi Perfil</v-btn>
    </template>

    <v-card>
      <v-card-title>
        <h2>Editar Perfil</h2>
      </v-card-title>

      <v-card-text>

        <v-form class="px-3">
          <v-container>
            <v-row>
              <v-col>
          <v-text-field outlined label="Nombre*" v-model="nombreUsuario">nombre</v-text-field>
              </v-col>
              <v-col>
          <v-text-field outlined label="Apellido*" v-model="apellidoUsuario" >apellido</v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
<!--                -->

                <template>
                  <v-menu
                      ref="menu"
                      v-model="menu"
                      :close-on-content-click="false"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                          outlined
                          v-model="date"
                          label="Fecha de Nacimiento"
                          append-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker
                        locale="es-ES"
                        ref="picker"
                        v-model="date"
                        :max="new Date().toISOString().substr(0, 10)"
                        min="1950-01-01"
                        @change="save"
                    ></v-date-picker>
                  </v-menu>
                </template>

<!--                <v-text-field outlined label="Fecha De Nacimiento" v-model="fechaNacUsuario"></v-text-field>-->
<!--                -->
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field outlined label="URL al Avatar" v-model="urlAvatar">URL</v-text-field>
              </v-col>
            </v-row>
          </v-container>



          <v-col>
            <v-row>
              <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
              <v-btn :disabled="loading"
                     color="grey lighten-1 white--text mx-0"
                     @click="cancelActionEditProfile">Cancelar</v-btn>
              <v-btn :loading="loading" flat class="primary mx-10" @click="submit">Guardar</v-btn>

            </v-row>
          </v-col>

        </v-form>
      </v-card-text>

    </v-card>
  </v-dialog>
</template>

<script>
import { UserApi } from "../API_EJS/js/user";
import state from "../store/state";

export default {
  name: "perfilPopUp",
  data(){
    return{
      loading: false,
      nombreUsuario: state.userFirstName,
      apellidoUsuario: state.userLastName,
      fechaNacUsuario: '',
      urlAvatar: state.userAvatar,
      date: null,
      menu: false,
    }
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.$refs.picker.activePicker = 'YEAR'))
    },
  },
  methods: {
    save (date) {
      this.$refs.menu.save(date)
    },
    cancelActionEditProfile: function (){
      console.log("CANCEL edit Profile");
      this.dialog = false;
      this.nombreUsuario='';
      this.usuario='';
      this.apellidoUsuario='';
      this.fechaNacUsuario='';
      this.urlAvatar='';
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    submit: async function (){
      this.loading = true;
      let dateAux = 20210511;
      if (this.date != null){
        dateAux = parseInt(this.date.replaceAll('-',''));
      }
      let avatarUrlAux = state.userAvatar;
      if (this.urlAvatar !== ''){
        avatarUrlAux = this.urlAvatar;
      }

      //console.log(this.date.replaceAll('-',''));
      const data = {  firstName: this.nombreUsuario,
        lastName: this.apellidoUsuario,
        gender: "male",
        birthdate: dateAux,
        phone: "98295822",
        avatarUrl: avatarUrlAux,
        metadata: null};
      console.log(data);
      await UserApi.modifyUserInformation(data, null);
      this.loading = false;
      //await this.loadingAnimation();
      await this.$store.dispatch("getUserInformation");
      this.cancelActionEditProfile();
    },
    async loadingAnimation () {
      this.loading = true;
      console.log("Antes del promise");
      await new Promise(resolve => setTimeout(resolve, 2000));
      console.log("Despues del promise");
      this.loading = false;
    },
  },
  computed: {
    cardID(){
      return this.$store.state.cardID;
    }
  },

}
</script>

<style scoped>

</style>