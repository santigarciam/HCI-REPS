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
  <v-card-title><div><span>¿Está seguro que desea borrar la rutina </span><span style="color: #258be5;">"{{rutina.name}}"</span>?</div></v-card-title>
  <v-col text--center>
    <v-row>
      <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
      <v-btn color="grey lighten-1" dark flat class="mx-0" @click="cancelActionRut">CANCELAR</v-btn>
      <v-btn :loading="loading" dark color="#2679CC" class="mx-10" @click="deleteRut(rutina.id)">BORRAR</v-btn>

    </v-row>
  </v-col>

  <v-col></v-col>
</v-card>


</v-dialog>

</template>

<script>
import {cycleApi} from "../../API_EJS/js/cycles";
import {cycleExercisesApi} from "../../API_EJS/js/cycleExercises";
import {routineApi} from "../../API_EJS/js/routines";

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
      const cyclesIDaux = await cycleApi.getAll(id, null);
      for (const ciclo of cyclesIDaux.content) {
        const ejercicios = await cycleExercisesApi.getAll(ciclo.id,null);
        let ejs = ejercicios.content.exercise;
        for (const ej in ejs){
          await cycleExercisesApi.delete(ciclo.id, ej.id, null);
        }
        // DESCOMENTAR CUANDO LA API FUNCIONE BIEN  !!!!!!!!!!!!!!!!!!
      }
      await routineApi.delete(id);
      this.loading = false;
      this.cancelActionRut();
    },
  }
}
</script>