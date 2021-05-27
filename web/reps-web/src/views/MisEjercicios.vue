<template>
  <div class="MisEjercicios">
    <v-divider></v-divider>
    <v-col>
      <v-row class="mt-2">
        <search-field class="ml-3"></search-field>
        <v-spacer></v-spacer>
        <nuevo-ejercicio v-if="ejercicios.length !== 0"></nuevo-ejercicio>
      </v-row>
    </v-col>
    <ejercice-card :key="cardID"></ejercice-card>
    <div class="noRut mt-16" v-if="ejercicios.length === 0">
      <template>
        <v-container>
          <v-row>
            <v-progress-linear
                background-color="#00B2EB"
                color="#2679CC"
                height="4"
                indeterminate
                rounded
            ></v-progress-linear>
            <v-col></v-col>
          </v-row>
          <v-row
              align="center"
              justify="center">
            <v-col class="text-center">
              <h1 class="display-1 mb-4">
                <span>Comience a </span>
                <span class="colorReps boldText">crear ejercicios</span>
              </h1>
              <h4 class="font-weight-regular subheading">
                No hay ejercicios creados
              </h4>
            </v-col>
          </v-row>
          <v-row class="justify-center align-center">
              <nuevo-ejercicio class="justify-center align-center"></nuevo-ejercicio>
          </v-row>
          <v-row>
            <v-col></v-col>
            <v-progress-linear
                background-color="#00B2EB"
                color="#2679CC"
                height="4"
                indeterminate
                rounded
            ></v-progress-linear>
          </v-row>
        </v-container>
      </template>
    </div>
  </div>
</template>

<script>
import NuevoEjercicio from "@/components/MisEjercicios/nuevoEjercicio";
import SearchField from "@/components/filtrado/searchField";
import EjerciceCard from "@/components/MisEjercicios/ejerciceCard";
import {bus} from '../main'

export default {
  name: "MisEjercicios",
  components: {EjerciceCard, SearchField, NuevoEjercicio},
  computed: {
    cardID(){
      return this.$store.state.cardID;
    },
    ejercicios(){
      return this.$store.state.listaEjercicios;
    }
  },

  data(){
    return {
      busqueda: ""
    }
  },

  created(){
    bus.$on('busqueda/MisEjercicios', (data) =>{
      this.busqueda = data;
      this.buscar()
    })
  },

  mounted() {
    this.$store.dispatch("changeCardID");
  },

  methods: {
    buscar: function (){
      if (this.busqueda=="" || this.busqueda == null ){
        this.$store.dispatch("getExercises");
      }
      else {
        this.$store.dispatch("searchExercises", this.busqueda);
      }
    }
  }
}
</script>

<style scoped>
.boldText{
  font-weight:bold
}
.colorReps{
  color: #2679CC ;
}
</style>