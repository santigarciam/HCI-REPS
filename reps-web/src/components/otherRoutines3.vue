<template>
  <div class="text-center">
    <v-dialog
        v-model="dialog"
        width="500"
        v-for="rutina in rutinas" v-bind:key="rutina.id">
      <!-- v-for="rutina in data().rutinas" :key="rutina.tituloRut"  UNA LINEA MAS ARRIBA -->

      <template v-slot:activator="{ on, attrs }">
        <v-container class ="container_v_card pb-4">
          <v-card v-bind="attrs" v-on="on" hover @click.stop="dialog = true; + funcionAUX(rutina.id)" >
            <v-col>
              <v-row>
                <v-card-title class="mb-0 pb-0" v-model="tituloRut">{{ rutina.name }} <v-rating
                    v-model="rutina.rating"
                    half-increments
                    readonly
                    background-color="grey"
                    color="orange"
                    medium
                    class="ml-2"
                ></v-rating></v-card-title>
                <v-spacer></v-spacer>

                <div>

                  <v-btn
                      icon
                      plain
                      color="grey"
                      v-model="boton"
                      class="mt-4 mr-2"
                      v-on:click="mostrarSnack"

                  >
                    <v-icon>mdi-share</v-icon>
                  </v-btn>
                  <v-snackbar
                      v-model="snackbar"
                  >Se copio al clipboard el link de la rutina!</v-snackbar>



                  <v-btn class="mt-4 mr-3" plain icon v-on:click="changeFav(rutina.id)" :color="getColour(rutina.id)">
                    <v-icon>mdi-heart</v-icon>
                  </v-btn>
                </div>
              </v-row>


              <v-row class="text-left">
                <v-icon small class="align-center mr-0 ml-3" color="blue">mdi-account</v-icon>
                <v-card-subtitle class="blue--text ml-0 pl-1 mt-0 pt-0 pb-0 font-weight-bold" v-model="autorRut">{{ rutina.user.username }} </v-card-subtitle>
              </v-row>
              <v-row><v-card-subtitle class="mr-0 pr-0 font-weight-bold">Descripción: </v-card-subtitle>
                <v-card-subtitle class="ml-0 pl-1" v-model="descripcionRut">{{ rutina.detail }}</v-card-subtitle>
              </v-row> <!--              <v-card-subtitle v-model="durRut">Duracion: {{ rutina.durRut }}</v-card-subtitle>-->
            </v-col>


          </v-card>
        </v-container>

      </template>

      <detailed-routine v-bind:rutina="rutina"></detailed-routine>

    </v-dialog>
  </div>
</template>




<script>

import { cycleApi } from "../API_EJS/js/cycles";
import DetailedRoutine from "./detailedRoutine";
//import {cycleExercisesApi } from "../API_EJS/js/cycleExercises";

export default {
  components: {DetailedRoutine},
  methods: {
    data: function () {
      return {
        snackbar: false,
      }
    },

    getColour: function (id){
      if (this.$store.state.idFavoritas.includes(id)){
        return "red"
      }
      else {
        return "grey"
      }
    },
    mostrarSnack: function (event) {
      event.stopPropagation();
      console.log(this);
      this.snackbar = true;
      this.$store.dispatch('changeCardID');
      setTimeout(() => {
        this.$emit("yourEvent");
      }, this.timeout);
    },

    changeFav: function (event) {
      event.stopPropagation();
      console.log(this);
      this.$store.dispatch('changeCardID');
      if (this.$store.state.idFavoritas.includes(this)){
        this.$store.dispatch("deleteFavourite", this);
      }
      else {
        this.$store.dispatch("addFavourite", this);
      }

    },

    cancelActionRut: function (){
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    //////////////////////////////////////////////////////////////////////////////////
    funcionAUX: async function (id){
      console.log(id);
      this.cyclesOfRutine = await cycleApi.getAll(id, null);
      this.cyclesOfRutine = this.cyclesOfRutine.content;
      console.log(this.cyclesOfRutine);
    },
    funcionAUX2: function (){
      //console.log("llegooooooooooooooooooooo");
      return this.cyclesOfRutine;
    },
    /////////////////////////////////////////////////////////////////////////////////
  },

  computed: {
    rutinas(){
      return this.$store.state.otrasRutinas;
    },
    idFavoritas(){
      return this.$store.state.idFavoritas;
    },
    cardID(){
      return this.$store.state.cardID;
    },
    ejercicios(){
      return this.$store.state.listaEjercicios;
    },
    cyclesOfRutine(){
      console.log(this.$store.state.cyclesOfRutine);
      return this.$store.state.cyclesOfRutine;
     },
    exercisesOfCycle(){
      return this.$store.state.exersisesOfRoutineOnCycle;
    } ////// DESCOMENTAR CUANDO EL API FUNCIONE
  },

  mounted() {
    this.$store.dispatch("getRoutines", "");
    this.$store.dispatch("getFavourites", "");
    console.log("1")
  },
}
</script>