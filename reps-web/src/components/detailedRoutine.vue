<template>
    <v-card flat>
      <v-card-title>{{ rutina.name }} <v-icon v-if="isPrivate" color="black" class="ml-2">mdi-lock</v-icon><v-spacer></v-spacer>
        <v-btn v-if="!this.$route.fullPath.includes('/routines/')" plain icon v-on:click="cancelActionRut"><v-icon dark>
        mdi-close
      </v-icon></v-btn><v-btn  plain  class="primary text--white" to="/Descubrir"  v-if="this.$route.fullPath.includes('/routines/')">Descubre más rutinas <v-icon>mdi-arrow-right</v-icon></v-btn></v-card-title>
      <v-divider></v-divider>
      <v-card-subtitle></v-card-subtitle>
      <v-row class="text-left" v-if="showUsername" >
        <v-icon small class="align-center pb-5 ml-8" color="blue">mdi-account</v-icon>
        <v-card-subtitle class="blue--text ml-0 pl-1 mt-0 pt-0 pb-0 font-weight-bold" v-model="autorRut">{{ username }} </v-card-subtitle>
      </v-row>
      <v-card-subtitle class="black--text"><span class="font-weight-bold">Descripción: </span>{{ rutina.detail }}</v-card-subtitle>
      <v-card-subtitle class="black--text"><span class="font-weight-bold">Dificultad: </span>{{ translateDifficulty(rutina.difficulty) }}</v-card-subtitle>
      <v-card-subtitle class="black--text"><span class="font-weight-bold">Categoría: </span>{{ rutina.category.name }}</v-card-subtitle>
      <h4 class="mt-4 pl-6 mb-4">Ciclos</h4>

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
                    <v-card-text> Duración: {{ejs.duration}}</v-card-text>
                  </v-col>
                  <v-col>
                    <v-card-text> Repeticiones: {{ejs.repetitions}}</v-card-text>
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
  props: ['rutina', 'username', 'showUsername'],

  /*computed: {
   cyclesOfRutine(){
      console.log(this.$store.state.cyclesOfRutine);
      return this.$store.state.cyclesOfRutine;
    },
    exercisesOfCycle(){
      return this.$store.state.exersisesOfRoutineOnCycle;
    },
  },*/
  methods: {
    cancelActionRut: function () {
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    translateDifficulty: function (difficulty) {
      if (difficulty == 'rookie') {
        return "Novato"
      }
      if (difficulty == 'beginner') {
        return "Principiante"
      }
      if (difficulty == 'intermediate') {
        return "Intermedio"
      }
      if (difficulty == 'advanced') {
        return "Avanzado"
      }
      if (difficulty == 'expert') {
        return "Experto"
      }
    }
  },
  computed:{
    isPrivate(){
      if (! this.showUsername){
        return ! this.rutina.isPublic
      }
      else{
        return false
      }
    }
  }
}
</script>