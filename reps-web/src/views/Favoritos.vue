<template>
  <v-container fluid id="Favoritos">
    <v-row class="mt-2">
      <v-col>
        <v-row>
        <filtrar-por></filtrar-por>
        <order-by></order-by>
          <search-field></search-field>
          <v-spacer></v-spacer>
        </v-row>
        <v-row></v-row>
      </v-col>
    </v-row>
    <favourite-routines></favourite-routines>

    <div class="noRut mt-16" v-if="favoritos.length === 0">
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
                <span class="blue--text boldText">explorar rutinas</span>
                <span> ...</span>
              </h1>
              <h4 class="subheading">
                Encuentra rutinas de colegas en la seccion "Descubrir"
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

  </v-container>
</template>


<script>
import filtrarPor from "@/components/filtrarPor";
import OrderBy from "@/components/orderBy";
import SearchField from "@/components/searchField";
import FavouriteRoutines from "../components/favouriteRoutines";
export default {
  name: "Favoritos",
  components: {FavouriteRoutines, SearchField, OrderBy, filtrarPor},
  computed: {
    favoritos(){
      return this.$store.state.rutinasFavoritas;
    }
  }
}
</script>

<style scoped>
.boldText{
  font-weight:bold;
}
</style>