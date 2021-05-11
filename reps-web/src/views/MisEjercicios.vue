<!--<template>-->
<!--  <v-container fluid id="Mis Ejercicios">-->
<!--    <v-col>-->
<!--    <v-row class="mt-2">-->
<!--       <search-field></search-field>-->
<!--        <v-spacer></v-spacer>-->
<!--        <nuevo-ejercicio></nuevo-ejercicio>-->
<!--    </v-row>-->
<!--    </v-col>-->
<!--  <ejercice-card :key="cardID"></ejercice-card>-->
<!--  </v-container>-->
<!--</template>-->

<template>
  <div class="MisEjercicios">
    <v-divider></v-divider>
    <v-col>
      <v-row class="mt-2">
        <search-field class="ml-3"></search-field>
        <v-spacer></v-spacer>
        <nuevo-ejercicio></nuevo-ejercicio>
      </v-row>
    </v-col>

    <ejercice-card :key="cardID"></ejercice-card>
  </div>
</template>

<script>
import NuevoEjercicio from "@/components/nuevoEjercicio";
import SearchField from "@/components/searchField";
import EjerciceCard from "@/components/ejerciceCard";
import {bus} from '../main'

export default {
  name: "MisEjercicios",
  components: {EjerciceCard, SearchField, NuevoEjercicio},
  computed: {
    cardID(){
      return this.$store.state.cardID;
    }
  },

  data(){
    return {
      busqueda: ""
    }
  },

  created(){
    bus.$on('busqueda', (data) =>{
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

</style>