<template>
  <div class="text-center">
  <v-dialog v-model="dialog" width="800px" v-for="excercise in ejercicios " :key="excercise.id" >
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
      <v-card depressed class="ma-2" slot="activator" v-bind="attrs" v-on="on">
        <v-col>
          <v-row>
            <v-card-title>{{ excercise.name }}</v-card-title>
            <v-spacer></v-spacer>
            <edit-ej v-on:click="modifyExercise(excercise)"></edit-ej>
<!--        <edit-ej ></edit-ej>      -->
            <delete-confirmaticon></delete-confirmaticon>


          </v-row>
        </v-col>

      </v-card>
    </template>

    <v-card>
      <v-card-title>
        <h2>{{ excercise.name }}</h2>
      </v-card-title>

      <v-card-text>
        <v-card-title v-model="descripcionEj">Descripcion: {{ excercise.detail }}</v-card-title>
        <v-card-title v-model="multEj">Multimedia:</v-card-title>
        <v-card-title v-model="equipEj">Equipamiento:</v-card-title>
      </v-card-text>


    </v-card>
  </v-dialog>
  </div>
</template>

<script>
import EditEj from "@/components/editEj";
import { ExerciseApi } from "@/API_EJS/js/exercises";
import DeleteConfirmaticon from "@/components/deleteConfirmation";
//import {ExerciseApi} from "@/API_EJS/js/exercises";
export default {
  components: {DeleteConfirmaticon, EditEj},
  data: () => ({ // Esto es lo de clousure CREO
    // excercises:ExerciseApi.getAll().content
}),
  methods: {
    // retrieve: function (){
    //   this.excercises= ExerciseApi.getAll().content;
    // },
    modifyExercise: function (excercise){
      ExerciseApi.modify(excercise);
    },

  },
  computed: {
    ejercicios(){
      return this.$store.state.listaEjercicios;
    }
  },

  mounted() {
    this.$store.dispatch("getExercises");
  }
}

</script>