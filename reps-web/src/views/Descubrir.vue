<template>
  <v-container fluid id="Descubrir">
    <v-row class="mt-2">
      <v-col>
        <v-row>
          <filtrar></filtrar>
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


import Filtrar from "@/components/filtrar";
import OrderBy from "@/components/orderBy";
import SearchField from "@/components/searchField";
import OtherRoutines from "../components/otherRoutines";
import {bus} from "../main";
export default {
  name: "Descubrir",
  components: {OtherRoutines, SearchField, OrderBy, Filtrar},
  data(){
    return {
      params: "",

      categoria: "",
      dificultad:"",
      rating: "",
      busqueda: "",
      orden: "",
      direc: "asc"
    }
  },
  created(){
    bus.$on('busqueda/Descubrir', (data) =>{
      this.busqueda = data;
      console.log(this.busqueda)
      this.filtrar()
    })
    bus.$on('ordenar/Descubrir', (data) =>{
      this.orden = data;
      console.log(this.orden)
      this.filtrar()
    })
  },
  methods: {
    filtrar: function (){
      if (this.busqueda!= ""){
        if (this.params!=""){
          this.params += "&"
        }
        this.params += "search=" + this.busqueda
        console.log("jojo")
      }
      if (this.orden!= ""){
        if (this.params!=""){
          this.params += "&"
        }
        this.params += "orderBy=" + this.orden
      }
      console.log(this.params)
      this.$store.dispatch("getRoutines", this.params);
      this.params=""
    },
    /*buscar: function (){
      if (this.busqueda=="" || this.busqueda == null ){
        this.$store.dispatch("getRoutines");
      }
      else {
        this.$store.dispatch("searchRoutines", this.busqueda);
      }
    },

    ordenar: function (){
      this.$store.dispatch("sortRoutines", this.orden);
    }*/

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