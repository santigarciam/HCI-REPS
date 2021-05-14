<template>
    <v-card flat>
      <v-card-title>{{ rutina.name }}<v-spacer></v-spacer>
        <v-btn v-if="!this.$route.fullPath.includes('/routines/')" plain icon v-on:click="cancelActionRut"><v-icon dark>
        mdi-close
      </v-icon></v-btn></v-card-title>
      <v-divider></v-divider>
      <v-card-subtitle></v-card-subtitle>
      <v-row class="text-left" v-if="this.$route.fullPath.includes('/routines/')" >
        <v-icon small class="align-center pb-5 ml-8" color="blue">mdi-account</v-icon>
        <v-card-subtitle class="blue--text ml-0 pl-1 mt-0 pt-0 pb-0 font-weight-bold" v-model="autorRut">{{ rutina.user.username }} </v-card-subtitle>
      </v-row>
      <v-card-subtitle>Descripcion: {{ rutina.detail }}</v-card-subtitle>
      <h4 class="pl-6 mb-4">Ciclos:</h4>

      <v-expansion-panels  v-for="ciclo in rutina.ciclosRut" :key="ciclo.id">
        <v-expansion-panel>
          <v-expansion-panel-header>
            {{ciclo.name}}
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-chip class="mb-4 ma-2" small color="primary">x{{ciclo.repetitions}}</v-chip>
            <template v-for="ejs in ciclo.ciclosEjs">

              <v-card small  class="mt-1" :key="ejs.exercise.id">

                <v-row>
                  <v-col>
                    <v-card-text>{{ejs.exercise.name}}</v-card-text>
                  </v-col> <v-col>
                </v-col>
                  <v-spacer></v-spacer>
                  <v-col>
                    <v-card-text> Duracion: {{ejs.duration}}</v-card-text>
                  </v-col>
                  <v-col>
                    <v-card-text> REPS: {{ejs.repetitions}}</v-card-text>
                  </v-col>
                </v-row>

              </v-card>

            </template>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-card>
</template>

<script>
export default {
  props: ['rutina'],
  /*computed: {
   cyclesOfRutine(){
      console.log(this.$store.state.cyclesOfRutine);
      return this.$store.state.cyclesOfRutine;
    },
    exercisesOfCycle(){
      return this.$store.state.exersisesOfRoutineOnCycle;
    },
  },*/
  methods:{
    cancelActionRut: function (){
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    }
  }
}
</script>