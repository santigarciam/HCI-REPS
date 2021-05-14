<template>
  <div class="MisRutinas">
    <v-divider></v-divider>
<v-col>
    <v-row class="mt-2">
    <filtrar-por></filtrar-por>
      <order-by></order-by>
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
                <span class="blue--text boldText">crear rutinas</span>
                <span> ...</span>
              </h1>
              <h4 class="subheading">
                No tienes ninguna creada
              </h4>
            </v-col>
          </v-row>
          <v-row>
            <v-spacer></v-spacer>
            <v-col class="text-center">
            <nueva-rutina></nueva-rutina>
            </v-col>
            <v-spacer></v-spacer>
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
  </div>
</template>

<script>

import RutineCard from "@/components/MisRutinas/myRoutines";
import NuevaRutina from "@/components/MisRutinas/nuevaRutina";
import FiltrarPor from "@/components/filtrado/filtrarPor";
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
      direc: "asc"

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
.boldText{
  font-weight:bold
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