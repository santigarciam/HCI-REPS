<template>
  <v-dialog persistent v-model="dialog" width="800px" :key="cardID">
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
      <v-btn depressed class="ma-2 mr-4" slot="activator" v-bind="attrs" v-on="on" outlined>+Anadir</v-btn>
    </template>

    <v-card>
      <v-card-title class="justify-center heigh font-weight-bold white--text colorReps">
        AGREGAR NUEVO EJERCICIO
      </v-card-title>

      <v-card-text>
        <v-col></v-col>
        <v-form class="px-3">
          <v-text-field outlined label="Nombre" v-model="nameEj"></v-text-field>
          <v-textarea outlined label="Descripcion" v-model="detailEj" ></v-textarea>
        </v-form>
      </v-card-text>

      <v-col>
        <v-row>
          <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
          <v-btn  color="grey lighten-1 white--text mx-0" v-on:click="cleanAndCloseDialog">Cancelar</v-btn>
          <v-btn :loading="loading" color="#2679CC" flat class="mx-10" v-on:click="addNewExcercise">Guardar</v-btn>

        </v-row>
      </v-col>
<v-col></v-col>
    </v-card>
  </v-dialog>
</template>

<script>
import { ExerciseApi } from "@/API_EJS/js/exercises";

export default {

  name: "nuevaRutina",
  data(){
    return{
      nameEj:'',
      detailEj:'',
      matEj:'',
      loading: false,
    }
  },
  methods :{
    addNewExcercise: async function (){
      this.loading = true;
      await ExerciseApi.add({name:this.nameEj,detail:this.detailEj,type:"exercise",metadata:null},null);
      this.loading = false;
      this.cleanAndCloseDialog();
    },
    cleanAndCloseDialog: function (){
      this.dialog = false;
      this.nameEj = "";
      this.detailEj = "";
      this.matEj = "";
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
  },
  computed: {
    cardID(){
      return this.$store.state.cardID;
    }
  },
  mounted() {
    this.$store.dispatch("changeCardID");
  }
}
</script>

<style scoped>
  .colorReps{
    background-color: #2679CC;
    font-family: ;
  }
</style>