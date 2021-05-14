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
                color="primary lighten-2"
                buffer-value="0"
                stream
            ></v-progress-linear>
            <v-col></v-col>
          </v-row>
          <v-row
              align="center"
              justify="center"
          >
            <v-col
                class="text-center"
                cols="24"
            >
              <h1 class="display-1 font-weight-thin mb-4">
                <span>... Comienza a </span>
                <span class="blue--text boldText">crear ejercicios</span>
                <span> ...</span>
              </h1>
              <h4 class="subheading">
                No tienes ninguno creado
              </h4>
            </v-col>
          </v-row>
          <v-row>
            <v-col class="text-center"
                   cols="24">
              <nuevo-ejercicio></nuevo-ejercicio>
            </v-col>
          </v-row>
          <v-row>
            <v-col></v-col>
            <v-progress-linear
                color="primary lighten-2"
                buffer-value="0"
                stream
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
      console.log(this.busqueda)
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
</style>