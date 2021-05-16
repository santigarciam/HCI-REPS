<template>
  <v-container fluid id="Descubrir">
    <v-row class="mt-2">
      <v-col>
        <v-row>
          <filtrar></filtrar>
          <order-by></order-by>
          <v-btn class="mt-3 mr-6" icon outlined @click="changeDir()">
            <v-icon>{{this.icon}}</v-icon>
          </v-btn>
          <v-spacer></v-spacer>
          <search-field class=" mr-4"></search-field>

        </v-row>
      </v-col>
    </v-row>
    <other-routines :key="cardID"></other-routines>
  </v-container>
</template>
<script>


import Filtrar from "@/components/filtrado/filtrar";
import OrderBy from "@/components/filtrado/orderBy";
import SearchField from "@/components/filtrado/searchField";
import OtherRoutines from "../components/Descubrir/otherRoutines2";
import {bus} from "../main";
export default {
  name: "Descubrir",
  components: {OtherRoutines, SearchField, OrderBy, Filtrar},
  data(){
    return {
      params: "",
      lengthAtStart: this.$store.state.otrasRutinas,
      categoria: "",
      dificultad:"",
      rating: "",
      busqueda: "",
      orden: "",
      direc: "asc",
      icon: "mdi-arrow-up"
    }
  },
  created(){
    bus.$on('busqueda/Descubrir', (data) =>{
      this.busqueda = data;
      this.filtrar()
    })
    bus.$on('ordenar/Descubrir', (data) =>{
      this.orden = data;
      this.filtrar()
    })
    bus.$on('dificultad/Descubrir', (data) =>{
      this.dificultad = data;
      this.filtrar()
    })
  },
  methods: {
    changeDir: function (){
      if (this.direc == "asc"){
        this.direc = "desc"
        this.icon = "mdi-arrow-down"
      }
      else{
        this.direc = "asc"
        this.icon = "mdi-arrow-up"
      }
      this.filtrar()
    },
    filtrar: function (){
      if (this.dificultad!= ""){
        if (this.params!=""){
          this.params += "&"
        }
        this.params += "difficulty=" + this.dificultad
      }
      if (this.busqueda!= ""){
        if (this.params!=""){
          this.params += "&"
        }
        this.params += "search=" + this.busqueda
      }
      if (this.orden!= ""){
        if (this.params!=""){
          this.params += "&"
        }
        this.params += "orderBy=" + this.orden
      }
      if (this.params!=""){
        this.params += "&"
      }
        this.params += "direction=" + this.direc
      this.$store.dispatch("getRoutines", this.params);
      this.params=""

    },
    removeListeners: function (){
      bus.$off('ordenar/Descubrir');
      bus.$off('busqueda/Descubrir');
      bus.$off('dificultad/Descubrir');
      }

  },
  mounted() {
    this.$store.dispatch("changeCardID");
  },
  beforeDestroy() {
    this.removeListeners();
  },
  computed: {
    cardID(){
      return this.$store.state.cardID;
    },
    otherRoutinesLength(){
      return this.$store.state.lengthAuxOtrasRutinas;
    }
  },
  beforeCreate() {
    this.$store.dispatch("getRoutines", "");
  }
}
</script>

<style >
.boldText{
  font-weight:bold;
}
.fixed{
  position: fixed;
}
</style>