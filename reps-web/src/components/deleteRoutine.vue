<template>

<v-dialog v-model="deleteConfi"  width="800px">
<template  v-slot:activator="{ on, attrs }">
  <v-btn icon plain class="mt-4 mr-3" color = "red" slot="activator" small  v-bind="attrs" v-on="on">
    <v-icon>
      mdi-delete
    </v-icon>
  </v-btn>
</template>

<v-card>
  <v-card-title>¿Está seguro que desea borrar esta rutina?</v-card-title>
  <v-col text--center>
    <v-row>
      <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
      <v-btn flat class="primary mx-0" @click="cancelActionRut">No</v-btn>
      <v-btn :loading="loading" color="grey lighten-1 white--text mx-10" @click="deleteRut(rutina.id)">Si</v-btn>

    </v-row>
  </v-col>

  <v-col></v-col>
</v-card>


</v-dialog>

</template>

<script>
import {cycleApi} from "../API_EJS/js/cycles";
import {cycleExercisesApi} from "../API_EJS/js/cycleExercises";
import {routineApi} from "../API_EJS/js/routines";

export default {
  props: ['rutina'],

  data(){
    return {
      loading: false
    }
  },

  computed: {
    cyclesOfRutine(){
      console.log(this.$store.state.cyclesOfRutine);
      return this.$store.state.cyclesOfRutine;
    },
    exercisesOfCycle(){
      return this.$store.state.exersisesOfRoutineOnCycle;
    },
  },

  methods:{
    cancelActionRut: function (){
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    deleteRut: async function (id) {
      this.loading = true;
      const cyclesIDaux = await cycleApi.getAll(parseInt(id), null);
      for (const ciclo of cyclesIDaux.content) {
        const ejercicios = await cycleExercisesApi.getAll(parseInt(ciclo.id),null);
        let ejs = ejercicios.content.exercise;
        for (const ej in ejs){
          await cycleExercisesApi.delete(parseInt(ciclo.id), parseInt(ej.id), null);
        }
        // DESCOMENTAR CUANDO LA API FUNCIONE BIEN  !!!!!!!!!!!!!!!!!!
      }
      await routineApi.delete(parseInt(id));
      this.loading = false;
      this.cancelActionRut();
    },
  }
}
</script>