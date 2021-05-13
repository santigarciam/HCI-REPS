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
      params: "",

      categoria: "",
      dificultad:"",
      rating: "",
      busqueda: "",
      orden: "",
      direc: "asc"

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
    bus.$on('busqueda/MisRutinas', (data) =>{
      this.busqueda = data;
      console.log(this.busqueda)
      this.filtrar()
    })
    bus.$on('ordenar/MisRutinas', (data) =>{
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
     this.$store.dispatch("getUserRoutines", this.params);
     this.params=""
   },
   removeListeners: function (){
     bus.$off('ordenar/MisRutinas');
     bus.$off('busqueda/MisRutinas');
   }
  },
  beforeDestroy() {
    this.removeListeners();
  },
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