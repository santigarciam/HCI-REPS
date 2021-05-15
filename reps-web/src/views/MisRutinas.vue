<template>
  <div class="MisRutinas">
    <v-divider></v-divider>
<v-col>
    <v-row class="mt-2">
    <filtrar-por></filtrar-por>
      <order-by></order-by>
      <v-btn class="mt-3 mr-6" icon outlined @click="changeDir()">
        <v-icon>{{this.icon}}</v-icon>
      </v-btn>
      <search-field></search-field>
      <v-spacer></v-spacer>
      <nueva-rutina  v-if="rutinas.length !== 0"></nueva-rutina>
    </v-row>
</v-col>
    <rutine-card :key="cardID"></rutine-card>

    <div class="noRut mt-16" v-if="rutinas.length === 0">
      <template>
        <v-container>
          <v-row>
            <v-progress-linear
                background-color="#00B2EB"
                color="#2679CC"
                height="4"
                indeterminate
                rounded
            ></v-progress-linear>
            <v-col></v-col>
          </v-row>
          <v-row
              align="center"
              justify="center" >
            <v-col class="text-center">
              <h1 class="display-1  mb-4">
                <span>Comience a </span>
                <span class="boldText colorReps">crear rutinas</span>
              </h1>
              <h4 class="font-weight-regular subheading">
                No hay rutinas creadas
              </h4>
            </v-col>
          </v-row>
          <v-row class="justify-center align-center">
            <nueva-rutina class="align-center justify-center"></nueva-rutina>
          </v-row>
          <v-row>
            <v-col></v-col>
            <v-progress-linear
                background-color="#00B2EB"
                color="#2679CC"
                height="4"
                indeterminate
                rounded
            ></v-progress-linear>
          </v-row>
        </v-container>
      </template>
    </div>
  </div>
</template>

<script>

import RutineCard from "@/components/MisRutinas/myRoutines";
import NuevaRutina from "@/components/MisRutinas/nuevaRutina";
import FiltrarPor from "@/components/filtrado/filtrar";
import OrderBy from "@/components/filtrado/orderBy";
import SearchField from "@/components/filtrado/searchField";
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
      direc: "asc",
      icon: "mdi-arrow-up"

    }
  },
  mounted() {
    this.$store.dispatch("changeCardID");
  },
  computed: {
    cardID(){
      return this.$store.state.cardID;
    },
      rutinas(){
        return this.$store.state.listaRutinas;
      },
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
    bus.$on('dificultad/MisRutinas', (data) =>{
      this.dificultad = data;
      console.log(this.dificultad)
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
     console.log(this.params)
     this.$store.dispatch("getUserRoutines", this.params);
     this.params=""
   },
   removeListeners: function (){
     bus.$off('ordenar/MisRutinas');
     bus.$off('busqueda/MisRutinas');
     bus.$off('dificultad/MisRutinas');
   }
  },
  beforeDestroy() {
    this.removeListeners();
  },
};
</script>

<style scoped>
.boldText{
  font-weight:bold
}
.colorReps{
  color: #2679CC ;
}

.noRut {
  display: flex;
  /*align-items: center;*/
  justify-content: center;
  height: 100vh;
}
.container_v_card{
  /*margin: auto;*/
  /*width:75%;*/
  /*height:50%;*/
}
.MisRutinas{
  margin: auto ;
}
</style>