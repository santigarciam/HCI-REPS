<template>
  <v-container fluid id="Descubrir">
    <v-row class="mt-2">
      <v-col>
        <v-row>
          <filtrar-por></filtrar-por>
          <order-by></order-by>
          <search-field></search-field>
          <v-spacer></v-spacer>
        </v-row>
      </v-col>
    </v-row>
    <other-routines></other-routines>
  </v-container>
</template>
<script>


import FiltrarPor from "@/components/filtrarPor";
import OrderBy from "@/components/orderBy";
import SearchField from "@/components/searchField";
import OtherRoutines from "../components/otherRoutines";
import {bus} from "../main";
export default {
  name: "Descubrir",
  components: {OtherRoutines, SearchField, OrderBy, FiltrarPor},
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
  methods: {
    buscar: function (){
      if (this.busqueda=="" || this.busqueda == null ){
        this.$store.dispatch("getRoutines");
      }
      else {
        this.$store.dispatch("searchRoutines", this.busqueda);
      }
    }
  }
}
</script>

<style >
.fixed{
  position: fixed;
}
</style>