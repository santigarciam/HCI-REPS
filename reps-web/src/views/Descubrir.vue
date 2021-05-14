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
    </div>

  </v-container>
</template>
<script>


import Filtrar from "@/components/filtrarPor";
import OrderBy from "@/components/orderBy";
import SearchField from "@/components/searchField";
import OtherRoutines from "../components/otherRoutines2";
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
      direc: "asc"
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
    removeListeners: function (){
      bus.$off('ordenar/Descubrir');
      bus.$off('busqueda/Descubrir');
      }

  },
  beforeUpdate() {
    this.$store.dispatch("changeCardID");
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