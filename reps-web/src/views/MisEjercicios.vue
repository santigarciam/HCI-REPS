<template>
  <v-container fluid id="Mis Ejercicios">

    <v-row class="mt-2">
      <v-col>
       <search-field></search-field>
      </v-col>
      <v-spacer></v-spacer>
      <nuevo-ejercicio></nuevo-ejercicio>
    </v-row>


  <ejercice-card :key="cardID"></ejercice-card>
  </v-container>
</template>

<script>
import NuevoEjercicio from "@/components/nuevoEjercicio";
import SearchField from "@/components/searchField";
import EjerciceCard from "@/components/ejerciceCard";
export default {
  name: "MisEjercicios",
  components: {EjerciceCard, SearchField, NuevoEjercicio},
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

</style>