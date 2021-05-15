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
<!--v-bind:params="this.params"
    <div class="noRut mt-16" v-if="this.otherRoutinesLength === 0">
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
                <span>... Se el primero en </span>
                <span class="blue--text boldText">compartir rutinas con tus colegas</span>
                <span> ...</span>
              </h1>
              <h4 class="subheading">
                Aqui apareceran rutinas creadas por otros usuarios
              </h4>
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
    </div>-->

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
    console.log("created")
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
    bus.$on('dificultad/Descubrir', (data) =>{
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
        console.log("jojo")
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
    //console.log("mounted")
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