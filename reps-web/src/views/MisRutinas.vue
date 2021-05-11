<template>
  <div class="MisRutinas">
    <v-divider></v-divider>
<v-col>
    <v-row class="mt-2">
    <filtrar-por></filtrar-por>
      <order-by></order-by>
      <search-field></search-field>
      <v-spacer></v-spacer>
      <nueva-rutina></nueva-rutina>
    </v-row>
</v-col>

    <rutine-card :key="cardID"></rutine-card>
  </div>
</template>

<script>

import RutineCard from "@/components/rutineCard";
import NuevaRutina from "@/components/nuevaRutina";
import FiltrarPor from "@/components/filtrarPor";
import OrderBy from "@/components/orderBy";
import SearchField from "@/components/searchField";
import {bus} from "../main";

export default {
  name: "MisRutinas",
  components: {
    SearchField,
    OrderBy,
    FiltrarPor,
    NuevaRutina,
    RutineCard
  },
  data(){
    return {
      busqueda: ""
    }
  },
  mounted() {
    this.$store.dispatch("changeCardID");
  },
  computed: {
    cardID(){
      return this.$store.state.cardID;
    }
  },
  created(){
   bus.$on('busqueda', (data) =>{
     this.busqueda = data;
     console.log(this.busqueda)
     this.buscar()
   })
 },
 methods: {
   buscar: function (){
     if (this.busqueda=="" || this.busqueda == null ){
       this.$store.dispatch("getUserRoutines");
     }
     else {
       this.$store.dispatch("searchUserRoutines", this.busqueda);
     }
   }
  }
};
</script>

<style scoped>
.container_v_card{
  /*margin: auto;*/
  /*width:75%;*/
  /*height:50%;*/
}
.MisRutinas{
  margin: auto ;

}
</style>