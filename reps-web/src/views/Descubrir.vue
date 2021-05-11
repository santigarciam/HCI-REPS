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
    <other-routines :key="cardID"></other-routines>
  </v-container>
</template>
<script>


import FiltrarPor from "@/components/filtrarPor";
import OrderBy from "@/components/orderBy";
import SearchField from "@/components/searchField";
import OtherRoutines from "../components/otherRoutines";
import {bus} from "../main";
//import {bus2} from "../main";
export default {
  name: "Descubrir",
  components: {OtherRoutines, SearchField, OrderBy, FiltrarPor},
  data(){
    return {
      busqueda: "",
      orden: ""
    }
  },
  created(){
    bus.$on('busqueda', (data) =>{
      this.busqueda = data;
      console.log(this.busqueda)
      this.buscar()
    })
    bus.$on('ordenar', (data) =>{
      this.orden = data;
      console.log(this.orden)
      this.ordenar()
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
    },

    ordenar: function (){
      this.$store.dispatch("sortRoutines", this.orden);
    }
  },
  mounted() {
    this.$store.dispatch("changeCardID");
  },
  computed: {
    cardID(){
      return this.$store.state.cardID;
    }
  }
}
</script>

<style >
.fixed{
  position: fixed;
}
</style>