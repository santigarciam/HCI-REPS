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
                <v-card-title v-model="tituloRut">{{ rutina.name }} <v-rating
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


                </div>
                <v-btn class="mt-4 mr-3" icon>
                  <v-icon>mdi-heart</v-icon>
                </v-btn>
              </v-row>

            </v-col>
            <v-col class="text-left">
              <v-card-subtitle v-model="autorRut">Autor: {{ rutina.user.username }} </v-card-subtitle>
              <v-card-subtitle v-model="descripcionRut">Descripcion: {{ rutina.detail }}</v-card-subtitle>
              <!--              <v-card-subtitle v-model="durRut">Duracion: {{ rutina.durRut }}</v-card-subtitle>-->
            </v-col>


          </v-card>
        </v-container>

      </template>

      <v-card flat>
        <v-card-title>{{ rutina.name }}</v-card-title>
        <!--        <v-btn v-on:click="getCiclosInID(parseInt(rutina.id))">BOTON</v-btn>-->
        <v-divider></v-divider>
        <v-card-subtitle></v-card-subtitle>
        <v-card-subtitle>Descripcion: {{ rutina.detail }}</v-card-subtitle>
        <!--        <v-card-subtitle>Duracion: {{ rutina.durRut }}</v-card-subtitle>-->
        <h4 class="pl-6">Ciclos:</h4>
        <template v-for="ciclo in funcionAUX2()">
          <v-list-item :key="ciclo">
            <template>
              <v-list-item-content>
                <v-list-item-title class="pl-6" v-text="ciclo.name"></v-list-item-title>

              </v-list-item-content>
            </template>
          </v-list-item>
        </template>
      </v-card>

    </v-dialog>
  </div>
</template>




<script>

import { cycleApi } from "../API_EJS/js/cycles";
//import {cycleExercisesApi } from "../API_EJS/js/cycleExercises";

export default {

  methods: {
    data: function () {
      return {
        snackbar: false,
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
    cardID(){
      return this.$store.state.cardID;
    },
  },

  mounted() {
    this.$store.dispatch("getRoutines", "");
  },
}
</script>