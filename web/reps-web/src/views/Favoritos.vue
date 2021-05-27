<template>
  <v-container fluid id="Favoritos">

  <favourite-routines :key="cardID" class="mt-2"></favourite-routines>
  <v-col></v-col>
    <empty-message class="mt-12" v-if="favoritos.length === 0" v-bind:mensaje1="this.mensaje1"
                   v-bind:mensaje2="this.mensaje2"
                   v-bind:subtitulo="this.subtitulo"
                   ></empty-message>


  </v-container>
</template>


<script>
import FavouriteRoutines from "../components/Favoritos/favouriteRoutines3";
import EmptyMessage from "../components/emptyMessage";
export default {
  name: "Favoritos",
  components: {EmptyMessage, FavouriteRoutines},
  data(){
    return{
      empty: false,
      mensaje1: "Agregue rutinas a ",
      mensaje2: "favoritos",
      subtitulo: "Encuentre rutinas de otros usuarios en la sección \"Descubrir\""
    }
  },
  mounted() {
    this.$store.dispatch("changeCardID");
    this.$store.dispatch("getFavourites", "");
  },
  created() {
    this.$store.dispatch("getFavourites", "");
  },
  computed: {
    favoritos() {
      return this.$store.state.rutinasFavoritas;
    },
    cardID(){
      return this.$store.state.cardID;
    },
  },
}
</script>

<style scoped>
.boldText{
  font-weight:bold;
}
</style>